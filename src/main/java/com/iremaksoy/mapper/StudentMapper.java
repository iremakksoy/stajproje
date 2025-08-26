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

    public static StudentDto toDto(student student) {
        if (student == null) {
            return null;
        }
        return new StudentDto(
            student.getFirstname(),
            student.getLastname(),
            student.getEmail(),
            student.getVersion()
        );
    }

}
