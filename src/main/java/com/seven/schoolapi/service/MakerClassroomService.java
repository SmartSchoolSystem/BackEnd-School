package com.seven.schoolapi.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.seven.schoolapi.dtos.MakerClassroomDTO;
import com.seven.schoolapi.entity.MakerClassroom;
import com.seven.schoolapi.repositories.MakerClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MakerClassroomService {

    @Autowired
    private MakerClassroomRepository makerClassroomRepository;

    public MakerClassroom createMakerClassroomFromDTO(MakerClassroomDTO makerClassroomDTO) {
        return new MakerClassroom(null, makerClassroomDTO.name());
    }

    public String registerMakerClassroom(MakerClassroom makerClassroom) {
        makerClassroomRepository.save(makerClassroom);
        return "Classroom registered successfully";
    }

    public boolean deleteMakerClassroom(Long id) {
        if (makerClassroomRepository.existsById(id)) {
            makerClassroomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<MakerClassroomDTO> getAllMakerClassrooms() {
        List<MakerClassroom> makerClassrooms = makerClassroomRepository.findAll();
        List<MakerClassroomDTO> makerClassroomDTOs = new ArrayList<>();

        for (MakerClassroom makerClassroom : makerClassrooms) {
            MakerClassroomDTO makerClassroomDTO = new MakerClassroomDTO(
                    makerClassroom.getId(),    // Retornar o ID
                    makerClassroom.getName()   // Retornar o nome
            );
            makerClassroomDTOs.add(makerClassroomDTO);
        }
        return makerClassroomDTOs;
    }


    public MakerClassroomDTO getMakerClassroomById(Long id) {
        MakerClassroom makerClassroom = makerClassroomRepository.findClassroomById(id)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found: " + id));
        return new MakerClassroomDTO(makerClassroom.getId(), makerClassroom.getName());
    }

    public MakerClassroomDTO getMakerClassroomByName(String name) {
        MakerClassroom makerClassroom = makerClassroomRepository.findClassroomByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found: " + name));
        return new MakerClassroomDTO(makerClassroom.getId(), makerClassroom.getName());
    }

    public String updateMakerClassroom(Long id, MakerClassroomDTO makerClassroomDTO) {
        if (!makerClassroomRepository.existsById(id)) {
            throw new IllegalArgumentException("Classroom not found: " + id);
        }
        MakerClassroom makerClassroom = new MakerClassroom(id, makerClassroomDTO.name());
        makerClassroomRepository.save(makerClassroom);
        return makerClassroom.getName() + " updated successfully";
    }

    public String patchMakerClassroom(Long id, MakerClassroomDTO makerClassroomDTO) {
        MakerClassroom makerClassroom = makerClassroomRepository.findClassroomById(id)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found: " + id));
        if (makerClassroomDTO.name() != null) {
            makerClassroom.setName(makerClassroomDTO.name());
        }
        makerClassroomRepository.save(makerClassroom);
        return makerClassroom.getName() + " updated successfully";
    }
}
