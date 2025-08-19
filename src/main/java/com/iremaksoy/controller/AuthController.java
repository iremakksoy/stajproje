package com.iremaksoy.controller;

import com.iremaksoy.Dto.LoginRequest;
import com.iremaksoy.Dto.LoginResponse;
import com.iremaksoy.Dto.RegisterRequest;
import com.iremaksoy.config.JwtUtils;
import com.iremaksoy.entities.student;
import com.iremaksoy.repository.IStudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Kimlik doğrulama işlemleri")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IStudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    @PostMapping("/register")
    @Operation(summary = "Öğrenci kaydı", description = "Yeni öğrenci kaydı oluşturur")
    public ResponseEntity<?> registerStudent(@RequestBody RegisterRequest registerRequest) {
        try {
            if (studentRepository.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest()
                        .body("Hata: Kullanıcı adı zaten kullanımda!");
            }

            if (studentRepository.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest()
                        .body("Hata: Email zaten kullanımda!");
            }

            student newStudent = new student();
            newStudent.setUsername(registerRequest.getUsername());
            newStudent.setEmail(registerRequest.getEmail());
            newStudent.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            newStudent.setFirstname(registerRequest.getFirstname() != null ? registerRequest.getFirstname() : "");
            newStudent.setLastname(registerRequest.getLastname() != null ? registerRequest.getLastname() : "");
            newStudent.setRole(student.Role.STUDENT);

            studentRepository.save(newStudent);

            return ResponseEntity.ok("Öğrenci başarıyla kaydedildi!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Kayıt sırasında hata oluştu: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Öğrenci girişi", description = "Öğrenci girişi yapar ve JWT token döner")
    public ResponseEntity<?> authenticateStudent(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );

            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userPrincipal);

            // Session'a kullanıcı bilgilerini kaydet
            session.setAttribute("USER_ID", userPrincipal.getUsername());
            session.setAttribute("USER_ROLE", userPrincipal.getAuthorities());
            session.setAttribute("JWT_TOKEN", jwt);

            return ResponseEntity.ok(new LoginResponse(jwt, userPrincipal.getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Login sırasında hata oluştu: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Çıkış yapma", description = "Kullanıcı çıkışı yapar ve session'ı temizler")
    public ResponseEntity<?> logout(HttpSession session) {
        try {
            // Session'ı geçersiz kıl
            session.invalidate();
            return ResponseEntity.ok("Başarıyla çıkış yapıldı!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Çıkış sırasında hata oluştu: " + e.getMessage());
        }
    }

    @GetMapping("/session-info")
    @Operation(summary = "Session bilgisi", description = "Mevcut session bilgilerini döner")
    public ResponseEntity<?> getSessionInfo(HttpSession session) {
        try {
            String userId = (String) session.getAttribute("USER_ID");
            if (userId != null) {
                return ResponseEntity.ok(Map.of(
                    "userId", userId,
                    "userRole", session.getAttribute("USER_ROLE"),
                    "sessionId", session.getId(),
                    "isActive", true
                ));
            } else {
                return ResponseEntity.ok(Map.of("isActive", false));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Session bilgisi alınırken hata oluştu: " + e.getMessage());
        }
    }
}