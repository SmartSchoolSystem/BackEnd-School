package com.seven.schoolapi.service;

import com.seven.schoolapi.dtos.InputOutputDTO;
import com.seven.schoolapi.entity.InputOutput;
import com.seven.schoolapi.entity.Class;
import com.seven.schoolapi.entity.MakerClassroom;
import com.seven.schoolapi.entity.UserInfo;
import com.seven.schoolapi.repositories.ClassRepository;
import com.seven.schoolapi.repositories.InputOutputRepository;
import com.seven.schoolapi.repositories.MakerClassroomRepository;
import com.seven.schoolapi.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InputOutputService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private MakerClassroomRepository makerClassroomRepository;

    @Autowired
    private InputOutputRepository inputOutputRepository;

    public void createInputOutput(InputOutputDTO inputOutputDTO) {
        UserInfo teacher = userInfoRepository.findById(inputOutputDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        Class classroom = classRepository.findById(inputOutputDTO.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Sala de aula não encontrada"));
        MakerClassroom makerClassroom = makerClassroomRepository.findById(inputOutputDTO.getMakerClassroomId())
                .orElseThrow(() -> new RuntimeException("Maker Classroom não encontrado"));

        InputOutput inputOutput = new InputOutput();
        inputOutput.setName(inputOutputDTO.getName());
        inputOutput.setHorarioEntrada(inputOutputDTO.getHorarioEntrada());
        inputOutput.setHorarioSaida(inputOutputDTO.getHorarioSaida());
        inputOutput.setTeacher(teacher);
        inputOutput.setClassroom(classroom);
        inputOutput.setMakerClassroom(makerClassroom);

        inputOutputRepository.save(inputOutput);
    }

    public List<InputOutputDTO> getAllInputOutputs() {
        return inputOutputRepository.findAll()
                .stream()
                .map(inputOutput -> new InputOutputDTO(
                        inputOutput.getId(),
                        inputOutput.getName(),
                        inputOutput.getHorarioEntrada(),
                        inputOutput.getHorarioSaida(),
                        inputOutput.getTeacher().getId(),
                        inputOutput.getClassroom().getIdClass(),
                        inputOutput.getMakerClassroom().getId()))
                .collect(Collectors.toList());
    }

    public InputOutputDTO getInputOutputById(Long id) {
        InputOutput inputOutput = inputOutputRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("InputOutput não encontrado com id: " + id));
        return new InputOutputDTO(
                inputOutput.getId(),
                inputOutput.getName(),
                inputOutput.getHorarioEntrada(),
                inputOutput.getHorarioSaida(),
                inputOutput.getTeacher().getId(),
                inputOutput.getClassroom().getIdClass(),
                inputOutput.getMakerClassroom().getId());
    }

    public String updateInputOutput(Long id, InputOutputDTO inputOutputDTO) {
        InputOutput inputOutput = inputOutputRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("InputOutput não encontrado com id: " + id));
        inputOutput.setName(inputOutputDTO.getName());
        inputOutput.setHorarioEntrada(inputOutputDTO.getHorarioEntrada());
        inputOutput.setHorarioSaida(inputOutputDTO.getHorarioSaida());
        UserInfo teacher = userInfoRepository.findById(inputOutputDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        Class classroom = classRepository.findById(inputOutputDTO.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Sala de aula não encontrada"));
        MakerClassroom makerClassroom = makerClassroomRepository.findById(inputOutputDTO.getMakerClassroomId())
                .orElseThrow(() -> new RuntimeException("Maker Classroom não encontrado"));

        inputOutput.setTeacher(teacher);
        inputOutput.setClassroom(classroom);
        inputOutput.setMakerClassroom(makerClassroom);

        inputOutputRepository.save(inputOutput);
        return "InputOutput atualizado com sucesso";
    }

    public boolean deleteInputOutput(Long id) {
        if (inputOutputRepository.existsById(id)) {
            inputOutputRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
