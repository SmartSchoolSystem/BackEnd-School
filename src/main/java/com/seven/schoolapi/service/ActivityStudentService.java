package com.seven.schoolapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seven.schoolapi.entity.Activity;
import com.seven.schoolapi.entity.Student;
import com.seven.schoolapi.entity.ActivityStudent;

import com.seven.schoolapi.dtos.ActivityStudentDTO;

import com.seven.schoolapi.repositories.ActivityStudentRepository;
import com.seven.schoolapi.repositories.StudentRepository;
import com.seven.schoolapi.repositories.ActivityRepository;

@Service
public class ActivityStudentService {

        @Autowired
        private ActivityStudentRepository activityStudentRepository;

        @Autowired
        private StudentRepository studentRepository;

        @Autowired
        private ActivityRepository activityRepository;

        public void createActivityStudent(ActivityStudentDTO activityStudentDTO) {
                Activity activity = activityRepository.findById(activityStudentDTO.activity())
                                .orElseThrow(() -> new RuntimeException("Activity não encontrada"));

                Student student = studentRepository.findByEnrollment(activityStudentDTO.student())
                                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

                ActivityStudent activityStudent = new ActivityStudent();
                activityStudent.setStudent(student);
                activityStudent.setActivity(activity);
                activityStudent.setNota(activityStudentDTO.nota());

                activityStudentRepository.save(activityStudent);
        }

        public List<ActivityStudentDTO> getAllActivityStudents() {
                return activityStudentRepository.findAll()
                                .stream()
                                .map(activityStudent -> new ActivityStudentDTO(
                                                activityStudent.getId(),
                                                activityStudent.getActivity().getId(),
                                                activityStudent.getStudent().getEnrollment(),
                                                activityStudent.getNota()))
                                .collect(Collectors.toList());
        }

        public ActivityStudentDTO getActivityStudentById(Long id) {
                ActivityStudent activityStudent = activityStudentRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "ActivityStudent não encontrada com id: " + id));

                return new ActivityStudentDTO(
                                activityStudent.getId(),
                                activityStudent.getActivity().getId(),
                                activityStudent.getStudent().getEnrollment(),
                                activityStudent.getNota());
        }

        public String updateActivityStudent(Long id, ActivityStudentDTO activityStudentDTO) {
                ActivityStudent activityStudent = activityStudentRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "ActivityStudent não encontrada com id: " + id));

                Activity activity = activityRepository.findById(activityStudentDTO.activity())
                                .orElseThrow(() -> new RuntimeException("Activity não encontrada"));

                Student student = studentRepository.findByEnrollment(activityStudentDTO.student())
                                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

                activityStudent.setActivity(activity);
                activityStudent.setStudent(student);
                activityStudent.setNota(activityStudentDTO.nota());

                activityStudentRepository.save(activityStudent);

                return "ActivityStudent atualizada com sucesso";
        }

        public boolean deleteActivityStudent(Long id) {
                if (activityStudentRepository.existsById(id)) {
                        activityStudentRepository.deleteById(id);
                        return true;
                } 
                return false;
        }
}
