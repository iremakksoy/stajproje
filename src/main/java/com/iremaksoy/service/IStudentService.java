package com.iremaksoy.service;

import java.util.List;
import java.util.Optional;

import com.iremaksoy.Dto.StudentDto;
import com.iremaksoy.entities.student;

public interface IStudentService {
	public student saveStudent(StudentDto studentDTO);
	public List<StudentDto> getAllStudents();
	public Optional<StudentDto> updateStudent(Integer id, StudentDto studentDTO);
	
	// Delete metodları
	public boolean deleteStudentById(Integer id);
	public boolean deleteStudentByUsername(String username);
	public void deleteAllStudents();
	
	// Ek metodlar
	public Optional<StudentDto> getStudentById(Integer id);
	public Optional<StudentDto> getStudentByUsername(String username);
}
