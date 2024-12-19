package com.seven.schoolapi.service;

import com.seven.schoolapi.entity.Class;
import com.seven.schoolapi.entity.Student;
import com.seven.schoolapi.dtos.ClassDTO;
import com.seven.schoolapi.repositories.StudentRepository;
import com.seven.schoolapi.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Class createClassFromDTO(ClassDTO classDTO) {
        List<Student> studentEntities = classDTO.students().stream()
                .map(enrollment -> studentRepository.findByEnrollment(enrollment)
                        .orElseThrow(() -> new RuntimeException("Student not found with enrollment: " + enrollment))) // Gera exceção se não encontrado
                .collect(Collectors.toList());
        return new Class(classDTO.idClass(), classDTO.classRoom(), studentEntities);
    }

    public String registerClass(Class newClass) {
        classRepository.save(newClass);
        return "Class registered successfully!";
    }

    public List<ClassDTO> getAllClassrooms() {
        List<Class> classrooms = classRepository.findAll();
        return classrooms.stream()
                .map(classroom -> new ClassDTO(
                        classroom.getIdClass(),
                        classroom.getClassRoom(),
                        classroom.getStudents().stream().map(Student::getEnrollment).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public ClassDTO getClassroomById(Long idClass) {
        Class classroom = classRepository.findById(idClass)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + idClass));
        return new ClassDTO(
                classroom.getIdClass(),
                classroom.getClassRoom(),
                classroom.getStudents().stream().map(Student::getEnrollment).collect(Collectors.toList())
        );
    }

    public String deleteClass(Long idClass) {
        if (!classRepository.existsById(idClass)) {
            throw new RuntimeException("Class not found with ID: " + idClass);
        }
        classRepository.deleteById(idClass);
        return "Class deleted successfully!";
    }

    public Class updateClass(Long idClass, ClassDTO classDTO) {
        Class existingClass = classRepository.findById(idClass)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + idClass));

        existingClass.setClassRoom(classDTO.classRoom());

        // Atualiza a lista de estudantes
        List<Student> studentEntities = classDTO.students().stream()
                .map(enrollment -> studentRepository.findByEnrollment(enrollment)
                        .orElseThrow(() -> new RuntimeException("Student not found with enrollment: " + enrollment)))
                .collect(Collectors.toList());

        existingClass.setStudents(studentEntities);

        return classRepository.save(existingClass);
    }

    // Método para atualização parcial (PATCH)
    public Class partialUpdateClass(Long idClass, ClassDTO classDTO) {
        Class existingClass = classRepository.findById(idClass)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + idClass));

        // Atualiza somente o campo classRoom se ele estiver presente
        if (classDTO.classRoom() != null) {
            existingClass.setClassRoom(classDTO.classRoom());
        }

        // Atualiza a lista de estudantes apenas se ela foi fornecida
        if (classDTO.students() != null && !classDTO.students().isEmpty()) {
            List<Student> studentEntities = classDTO.students().stream()
                    .map(enrollment -> studentRepository.findByEnrollment(enrollment)
                            .orElseThrow(() -> new RuntimeException("Student not found with enrollment: " + enrollment)))
                    .collect(Collectors.toList());
            existingClass.setStudents(studentEntities);
        }

        return classRepository.save(existingClass);
    }
}
