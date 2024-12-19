package com.seven.schoolapi.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "classes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClass;

    @Column(nullable = false)
    private String classRoom;

    @ManyToMany
    @JoinTable(
            name = "class_students",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_enrollment")
    )
    private List<Student> students;
    public Class(Long idClass) {
        this.idClass = idClass;
    }
}
