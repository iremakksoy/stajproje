package com.iremaksoy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import com.iremaksoy.Dto.StudentDto;
import com.iremaksoy.entities.student;

public interface IStudentController {
	public student saveStudent(StudentDto studentDTO);
	public List<StudentDto> getAllStudents();
	
	// Delete metodları
	public ResponseEntity<String> deleteStudentById(Integer id);
	public ResponseEntity<String> deleteStudentByUsername(String username);
	public ResponseEntity<String> deleteAllStudents();
	
	// Ek metodlar
	public ResponseEntity<StudentDto> getStudentById(Integer id);
	public ResponseEntity<StudentDto> getStudentByUsername(String username);
}
