package com.seven.schoolapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seven.schoolapi.service.*;

@RestController
@RequestMapping("/delete")
public class DeleteController {
    
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

    @DeleteMapping("/student/{enrollment}")
    public ResponseEntity<String> deleteStudent(@PathVariable String enrollment) {
        try {
            boolean isDeleted = studentService.deleteStudent(enrollment);
            if (isDeleted) {
                return ResponseEntity.ok("Student deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/classmaker/{id}")
    public ResponseEntity<String> deleteClassroom(@PathVariable Long id) {
        try {
            boolean isDeleted = makerClassroomService.deleteMakerClassroom(id);
            if (isDeleted) {
                return ResponseEntity.ok("Classroom deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Classroom not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/classroom/{idClass}")
    public ResponseEntity<String> deleteClass(@PathVariable Long idClass) {
        String message = classService.deleteClass(idClass);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/inputoutput/{id}")
    public ResponseEntity<String> deleteInputOutput(@PathVariable Long id) {
        if (inputOutputService.deleteInputOutput(id)) {
            return ResponseEntity.ok("Entrada e saída deletadas com sucesso");
        }
        return ResponseEntity.status(404).body("Entrada e saída não encontradas");
    }

    @DeleteMapping("/activity/{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.ok("Activity deleted successfully");
    }

    @DeleteMapping("/activitystudent/{id}")
    public ResponseEntity<String> deleteActivityStudent(@PathVariable Long id) {
        activityStudentService.deleteActivityStudent(id);
        return ResponseEntity.ok("ActivityStudent deleted successfully");
    }
}
