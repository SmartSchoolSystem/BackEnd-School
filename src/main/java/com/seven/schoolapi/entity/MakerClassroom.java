package com.seven.schoolapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "makerClassroom")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakerClassroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    public MakerClassroom(Long id) {
        this.id = id;
    }
}
