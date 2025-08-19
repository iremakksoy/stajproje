package com.iremaksoy.controller.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.iremaksoy.Dto.StudentDto;
import com.iremaksoy.controller.IStudentController;
import com.iremaksoy.entities.student;
import com.iremaksoy.service.IStudentService;


@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Controller", description = "Student API")
public class StudentControllerImpl implements IStudentController {
	@Autowired 
	private IStudentService studentService;

    @PostMapping("/save")
    @Operation(summary = "Add a new student", description = "This endpoint is used to add a new student to the database")
    @Override
    public student saveStudent(@RequestBody StudentDto studentDTO) {
        return studentService.saveStudent(studentDTO);
    }
    
    @GetMapping("/list")
    @Operation(summary = "Get all students", description = "This endpoint returns a list of all students")
    @SecurityRequirement(name = "Bearer Authentication")
    @Override
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }
    
    // Delete metodları
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete student by ID", description = "This endpoint deletes a student by their ID")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<String> deleteStudentById(@PathVariable Integer id) {
        boolean deleted = studentService.deleteStudentById(id);
        if (deleted) {
            return ResponseEntity.ok("Student with ID " + id + " has been deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/delete/username/{username}")
    @Operation(summary = "Delete student by username", description = "This endpoint deletes a student by their username")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<String> deleteStudentByUsername(@PathVariable String username) {
        boolean deleted = studentService.deleteStudentByUsername(username);
        if (deleted) {
            return ResponseEntity.ok("Student with username '" + username + "' has been deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/delete/all")
    @Operation(summary = "Delete all students", description = "This endpoint deletes all students from the database")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<String> deleteAllStudents() {
        studentService.deleteAllStudents();
        return ResponseEntity.ok("All students have been deleted successfully.");
    }
    
    // Ek metodlar
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "This endpoint returns a student by their ID")
    @Override
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Integer id) {
        Optional<StudentDto> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    @Operation(summary = "Get student by username", description = "This endpoint returns a student by their username")
    @Override
    public ResponseEntity<StudentDto> getStudentByUsername(@PathVariable String username) {
        Optional<StudentDto> student = studentService.getStudentByUsername(username);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
