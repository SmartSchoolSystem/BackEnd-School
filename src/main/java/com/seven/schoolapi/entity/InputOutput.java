package com.seven.schoolapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "input_outputs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "horario_entrada", nullable = false)
    private String horarioEntrada;

    @Column(name = "horario_saida", nullable = false)
    private String horarioSaida;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private UserInfo teacher;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    private Class classroom;

    @ManyToOne
    @JoinColumn(name = "maker_classroom_id", nullable = false)
    private MakerClassroom makerClassroom;
}
