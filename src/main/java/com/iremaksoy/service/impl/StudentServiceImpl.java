package com.iremaksoy.service.impl;

import com.iremaksoy.Dto.StudentDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.iremaksoy.entities.student;
import com.iremaksoy.mapper.StudentMapper;
import com.iremaksoy.repository.IStudentRepository;
import com.iremaksoy.service.IStudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.OptimisticLockingFailureException;

@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;
    
    @Override
    public student saveStudent(StudentDto studentDTO) {
        student student = StudentMapper.toEntity(studentDTO);
        return studentRepository.save(student);
    }
    
    @Override
    public List<StudentDto> getAllStudents() {
        List<student> students = studentRepository.findAll();
        return students.stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }
    
    // Delete metodları
    @Override
    public boolean deleteStudentById(Integer id) {
        try {
            if (studentRepository.existsById(id)) {
                studentRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public boolean deleteStudentByUsername(String username) {
        try {
            Optional<student> studentOpt = studentRepository.findByUsername(username);
            if (studentOpt.isPresent()) {
                studentRepository.delete(studentOpt.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
    
    // Ek metodlar
    @Override
    public Optional<StudentDto> getStudentById(Integer id) {
        Optional<student> studentOpt = studentRepository.findById(id);
        return studentOpt.map(StudentMapper::toDto);
    }
    
    @Override
    public Optional<StudentDto> getStudentByUsername(String username) {
        Optional<student> studentOpt = studentRepository.findByUsername(username);
        return studentOpt.map(StudentMapper::toDto);
    }

    @Override
    public Optional<StudentDto> updateStudent(Integer id, StudentDto studentDTO) {
        Optional<student> existingOpt = studentRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return Optional.empty();
        }
        student existing = existingOpt.get();
        // client-stale check
        if (studentDTO.getVersion() == null || !studentDTO.getVersion().equals(existing.getVersion())) {
            throw new OptimisticLockingFailureException("Version conflict for student id=" + id);
        }
        existing.setFirstname(studentDTO.getFirstname());
        existing.setLastname(studentDTO.getLastname());
        existing.setEmail(studentDTO.getEmail());
        student saved = studentRepository.save(existing);
        return Optional.ofNullable(StudentMapper.toDto(saved));
    }
}
