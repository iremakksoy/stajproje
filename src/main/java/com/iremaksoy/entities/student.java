package com.iremaksoy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="student", schema = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class student implements UserDetails {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Version
	@Column(name="version")
	private Long version;
	
	@Column(name="first_name", nullable = false)
	private String firstname;
	
	@Column(name="last_name", nullable = false)
	private String lastname;
	
	@Column(name="email", nullable = false, unique = true)
	private String email;

	// Authentication alanları
	@Column(name="username", unique = true, nullable = false)
	private String username;

	@Column(name="password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Role role = Role.STUDENT;

	@Column(name="enabled")
	private boolean enabled = true;

	@Column(name="account_non_expired")
	private boolean accountNonExpired = true;

	@Column(name="account_non_locked")
	private boolean accountNonLocked = true;

	@Column(name="credentials_non_expired")
	private boolean credentialsNonExpired = true;

	public enum Role {
		STUDENT, ADMIN
	}

	// UserDetails interface metodları
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
