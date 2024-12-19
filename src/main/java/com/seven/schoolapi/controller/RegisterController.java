package com.seven.schoolapi.controller;

import com.seven.schoolapi.entity.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seven.schoolapi.entity.*;
import com.seven.schoolapi.dtos.*;
import com.seven.schoolapi.service.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private MakerClassroomService makerClassroomService;

    @Autowired
    private ClassService classService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private InputOutputService inputOutputService;

    @Autowired
    private ActivityStudentService activityStudentService;

    @PostMapping("/student")
    public ResponseEntity<String> registerStudent(@Valid @RequestBody StudentDTO studentDTO) {
        try {
            Student student = studentService.createStudentFromDTO(studentDTO);
            String message = studentService.registerStudent(student);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/classmaker")
    public ResponseEntity<String> registerClassroom(@Valid @RequestBody MakerClassroomDTO makerClassroomDTO) {
        try {
            MakerClassroom makerClassroom = makerClassroomService.createMakerClassroomFromDTO(makerClassroomDTO);
            String message = makerClassroomService.registerMakerClassroom(makerClassroom);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/classroom")
    public ResponseEntity<String> registerClass(@Valid @RequestBody ClassDTO classDTO) {
        try {
            if (classDTO.students() == null) {
                classDTO = new ClassDTO(classDTO.idClass(), classDTO.classRoom(), List.of());
            }

            Class newClass = classService.createClassFromDTO(classDTO);
            String message = classService.registerClass(newClass);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/inputoutput")
    public ResponseEntity<Void> createInputOutput(@RequestBody InputOutputDTO inputOutputDTO) {
        inputOutputService.createInputOutput(inputOutputDTO); // Chamada ao serviço
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna 201 Created
    }

    @PostMapping("/activity")
    public ResponseEntity<String> createActivity(@RequestBody ActivityDTO activityDTO) {
        activityService.createActivity(activityDTO);
        return ResponseEntity.ok("Activity created successfully");
    }

    @PostMapping("/activitystudent")
    public ResponseEntity<String> createActivityStudent(@RequestBody ActivityStudentDTO activityStudentDTO) {
        activityStudentService.createActivityStudent(activityStudentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}