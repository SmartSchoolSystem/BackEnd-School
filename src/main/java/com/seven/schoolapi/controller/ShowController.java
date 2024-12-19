package com.seven.schoolapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seven.schoolapi.dtos.*;
import com.seven.schoolapi.entity.UserInfo;
import com.seven.schoolapi.service.*;
import com.seven.schoolapi.service.userServices.*;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private UserInfoService service;
    
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

    @GetMapping("/student")
    public ResponseEntity<List<StudentDTO>> showStudent() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/student/{enrollment}")
    public ResponseEntity<StudentDTO> showStudentByEnrollment(@PathVariable String enrollment) {
        StudentDTO student = studentService.getStudentByEnrollment(enrollment);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/classroom")
    public ResponseEntity<List<ClassDTO>> showAllClassroom() {
        List<ClassDTO> classrooms = classService.getAllClassrooms();
        return ResponseEntity.ok(classrooms);
    }

    @GetMapping("/classroom/{idClass}")
    public ResponseEntity<ClassDTO> showClassroomById(@PathVariable Long idClass) {
        ClassDTO classroom = classService.getClassroomById(idClass);
        return ResponseEntity.ok(classroom);
    }

    @GetMapping("/classmaker")
    public ResponseEntity<List<MakerClassroomDTO>> showMakerClassroom() {
        List<MakerClassroomDTO> classrooms = makerClassroomService.getAllMakerClassrooms();
        return ResponseEntity.ok(classrooms);
    }
    
    @GetMapping("/classmaker/{id}")
    public ResponseEntity<MakerClassroomDTO> showMakerClassroomById(@PathVariable Long id) {
        MakerClassroomDTO classroom = makerClassroomService.getMakerClassroomById(id);
        return ResponseEntity.ok(classroom);
    }

    @GetMapping("/classmaker/{name}")
    public ResponseEntity<MakerClassroomDTO> showMakerClassroomByName(@PathVariable String name) {
        MakerClassroomDTO classroom = makerClassroomService.getMakerClassroomByName(name);
        return ResponseEntity.ok(classroom);
    }

    @GetMapping("/all/activity")
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<ActivityDTO> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }
    @GetMapping("activity/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        ActivityDTO activity = activityService.getActivityById(id);
        return ResponseEntity.ok(activity);
    }

    // Show secretary profile
    @GetMapping("/secretary/secretaryProfile")
    public ResponseEntity<String> secretaryProfile() {
        return ResponseEntity.ok("Welcome to SECRETARY Profile");
    }

    // Show teacher profile
    @GetMapping("/teacher/teacherProfile")
    public ResponseEntity<String> userProfile() {
        return ResponseEntity.ok("Welcome to TEACHER Profile");
    }

    // Show admin profile
    @GetMapping("/admin/adminProfile")
    public ResponseEntity<String> adminProfile() {
        return ResponseEntity.ok("Welcome to ADMIN Profile");
    }

    // List all users
    @GetMapping("/admin/allusers")
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/admin/userid/{id}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    // Get user by username
    @GetMapping("/admin/username/{username}")
    public ResponseEntity<UserInfo> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getUserByUsername(username));
    }

    // Get all secretaries
    @GetMapping("/admin/allsecretaries")
    public ResponseEntity<List<UserInfo>> getAllSecretaries() {
        return ResponseEntity.ok(service.getAllSecretaries());
    }

    // Get all teachers
    @GetMapping("/admin/allteachers")
    public ResponseEntity<List<UserInfo>> getAllTeachers() {
        return ResponseEntity.ok(service.getAllTeachers());
    }

    // Get users by role
    // I think that this method is useless, but i'l keep it here
    @GetMapping("/admin/role/{role}")
    public ResponseEntity<List<UserInfo>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(service.getUsersByRole(role));
    }
    @GetMapping("/all/inputoutput")
    public ResponseEntity<List<InputOutputDTO>> getAllInputOutputs() {
        List<InputOutputDTO> inputOutputs = inputOutputService.getAllInputOutputs();
        return ResponseEntity.ok(inputOutputs);
    }

    @GetMapping("inputoutput/{id}")
    public ResponseEntity<InputOutputDTO> getInputOutputById(@PathVariable Long id) {
        InputOutputDTO inputOutputDTO = inputOutputService.getInputOutputById(id);
        return ResponseEntity.ok(inputOutputDTO);
    }

    @GetMapping("/all/activitystudent")
    public ResponseEntity<List<ActivityStudentDTO>> getAllActivityStudents() {
        List<ActivityStudentDTO> activityStudents = activityStudentService.getAllActivityStudents();
        return ResponseEntity.ok(activityStudents);
    }

    @GetMapping("activitystudent/{id}")
    public ResponseEntity<ActivityStudentDTO> getActivityStudentById(@PathVariable Long id) {
        ActivityStudentDTO activityStudent = activityStudentService.getActivityStudentById(id);
        return ResponseEntity.ok(activityStudent);
    }
}
