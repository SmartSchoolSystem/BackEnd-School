package com.seven.schoolapi.service;

import com.seven.schoolapi.dtos.ActivityDTO;
import com.seven.schoolapi.entity.Activity;
import com.seven.schoolapi.entity.InputOutput;
import com.seven.schoolapi.repositories.ActivityRepository;
import com.seven.schoolapi.repositories.InputOutputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private InputOutputRepository inputOutputRepository;

    public void createActivity(ActivityDTO activityDTO) {
        InputOutput inputOutput = inputOutputRepository.findById(activityDTO.inputOutputId())
                .orElseThrow(() -> new RuntimeException("InputOutput não encontrado"));

        Activity activity = new Activity();
        activity.setName(activityDTO.name());
        activity.setValue(activityDTO.value());
        activity.setInputOutput(inputOutput);

        activityRepository.save(activity);
    }

    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll()
                .stream()
                .map(activity -> new ActivityDTO(
                        activity.getId(),
                        activity.getName(),
                        activity.getValue(),
                        activity.getInputOutput().getId()))
                .collect(Collectors.toList());
    }

    public ActivityDTO getActivityById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity não encontrada com id: " + id));

        return new ActivityDTO(
                activity.getId(),
                activity.getName(),
                activity.getValue(),
                activity.getInputOutput().getId());
    }

    public String updateActivity(Long id, ActivityDTO activityDTO) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity não encontrada com id: " + id));

        InputOutput inputOutput = inputOutputRepository.findById(activityDTO.inputOutputId())
                .orElseThrow(() -> new RuntimeException("InputOutput não encontrado"));

        activity.setName(activityDTO.name());
        activity.setValue(activityDTO.value());
        activity.setInputOutput(inputOutput);

        activityRepository.save(activity);
        return "Activity atualizada com sucesso";
    }

    public boolean deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
