package com.seven.schoolapi.controller;

import com.seven.schoolapi.entity.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seven.schoolapi.dtos.*;
import com.seven.schoolapi.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/update")
public class UpdateController {

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

    // Put method

    @PutMapping("/student/{enrollment}")
    public ResponseEntity<String> updateStudent(@PathVariable String enrollment, @RequestBody StudentDTO studentDTO) {
        try {
            String result = studentService.updateStudent(enrollment, studentDTO);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/classmaker/{id}")
    public ResponseEntity<String> updateMakerClassroom(@PathVariable Long id, @RequestBody MakerClassroomDTO makerClassroomDTO) {
        try {
            String result = makerClassroomService.updateMakerClassroom(id, makerClassroomDTO);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PatchMapping("classroom/{idClass}")
    public ResponseEntity<Class> partialUpdateClass(@PathVariable Long idClass, @RequestBody ClassDTO classDTO) {
        Class updatedClass = classService.partialUpdateClass(idClass, classDTO);
        return ResponseEntity.ok(updatedClass);
    }

    @PatchMapping("/student/{enrollment}")
    public ResponseEntity<String> patchUpdateStudent(@Valid @PathVariable String enrollment, @RequestBody StudentDTO studentDTO) {
        try {
            String result = studentService.patchUpdateStudent(enrollment, studentDTO);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PatchMapping("/classmaker/{id}")
    public ResponseEntity<String> patchUpdateClassroom(@Valid @PathVariable Long id, @RequestBody MakerClassroomDTO makerClassroomDTO) {
        try {
            String result = makerClassroomService.patchMakerClassroom(id, makerClassroomDTO);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("inputoutput/{id}")
    public ResponseEntity<String> updateInputOutput(@PathVariable Long id, @RequestBody InputOutputDTO inputOutputDTO) {
        String response = inputOutputService.updateInputOutput(id, inputOutputDTO);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/activity/{id}")
    public ResponseEntity<String> updateActivity(@PathVariable Long id, @RequestBody ActivityDTO activityDTO) {
        activityService.updateActivity(id, activityDTO);
        return ResponseEntity.ok("Activity updated successfully");
    }
}
