package com.iremaksoy.mapper;

import com.iremaksoy.Dto.StudentDto;
import com.iremaksoy.entities.student;


public class StudentMapper {
	public static student toEntity(StudentDto studentDTO) {
        student student = new student();
        student.setFirstname(studentDTO.getFirstname());
        student.setLastname(studentDTO.getLastname());
        student.setEmail(studentDTO.getEmail());
        return student;
    }
	

}
