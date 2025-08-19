package com.iremaksoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iremaksoy.entities.student;

import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<student,Integer>{
    // Authentication için eklenen metodlar
    Optional<student> findByUsername(String username);
    Optional<student> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
