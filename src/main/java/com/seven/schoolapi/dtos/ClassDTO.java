package com.seven.schoolapi.dtos;

import java.util.List;

public record ClassDTO(Long idClass, String classRoom, List<String> students) {
    public ClassDTO(Long idClass, String classRoom, List<String> students) {
        this.idClass = idClass;
        this.classRoom = classRoom;
        this.students = students != null ? students : List.of();
    }
}
