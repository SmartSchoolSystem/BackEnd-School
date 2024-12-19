package com.seven.schoolapi.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.seven.schoolapi.dtos.StudentDTO;
import com.seven.schoolapi.entity.Student;
import com.seven.schoolapi.repositories.StudentRepository;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    public Student createStudentFromDTO(StudentDTO studentDTO) {
        return new Student(studentDTO.enrollment(), studentDTO.name());
    }

    public String registerStudent(Student student) {
        if (studentRepository.existsByEnrollment(student.getEnrollment())) {
            throw new IllegalArgumentException("Enrollment already exists");
        }
        studentRepository.save(student);
        return "Student registered successfully";
    }
    @Transactional
    public boolean deleteStudent(String enrollment) {
        if (studentRepository.existsByEnrollment(enrollment)) {
            studentRepository.deleteByEnrollment(enrollment);
            return true;
        }
        return false;
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOs = new ArrayList<>();

        for (Student student : students) {
            StudentDTO studentDTO = new StudentDTO(
                    student.getEnrollment(),
                    student.getName()
            );
            studentDTOs.add(studentDTO);
        }
        return studentDTOs;
    }

    public StudentDTO getStudentByEnrollment(String enrollment) {
        Student student = studentRepository.findByEnrollment(enrollment)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + enrollment));
        return new StudentDTO(student.getEnrollment(), student.getName());
    }

    public StudentDTO getStudentByName(String name) {
        Student student = studentRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + name));
        return new StudentDTO(student.getEnrollment(), student.getName());
    }

    public String updateStudent(String enrollment, StudentDTO studentDTO) {
        Student student = studentRepository.findByEnrollment(enrollment)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + enrollment));
        student.setEnrollment(studentDTO.enrollment());
        student.setName(studentDTO.name());
        studentRepository.save(student);
        return student.getName() + " updated successfully";
    }

    public String patchUpdateStudent (String enrollment, StudentDTO studentDTO) {
        Student student = studentRepository.findByEnrollment(enrollment)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + enrollment));
        if (studentDTO.enrollment() != null) {
            student.setEnrollment(studentDTO.enrollment());
        }
        if (studentDTO.name() != null) {
            student.setName(studentDTO.name());
        }
        studentRepository.save(student);
        return student.getName() + " updated successfully";
    }
}
