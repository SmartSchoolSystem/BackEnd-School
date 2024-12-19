package com.seven.schoolapi.dtos;

public class InputOutputDTO {
    private Long id;
    private String name;
    private String horarioEntrada;
    private String horarioSaida;
    private Long teacherId;
    private Long classroomId;
    private Long makerClassroomId;

    public InputOutputDTO() {}

    public InputOutputDTO(Long id, String name, String horarioEntrada, String horarioSaida, Long teacherId, Long classroomId, Long makerClassroomId) {
        this.id = id;
        this.name = name;
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = horarioSaida;
        this.teacherId = teacherId;
        this.classroomId = classroomId;
        this.makerClassroomId = makerClassroomId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public String getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getMakerClassroomId() {
        return makerClassroomId;
    }

    public void setMakerClassroomId(Long makerClassroomId) {
        this.makerClassroomId = makerClassroomId;
    }
}
