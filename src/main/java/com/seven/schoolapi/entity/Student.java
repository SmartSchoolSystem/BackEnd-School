package com.seven.schoolapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Column(length = 11)
    private String enrollment;

    private String name;
    @ManyToMany(mappedBy = "students")
    private List<Class> classes;

    public Student(String enrollment, String name) {
        this.enrollment = enrollment;
        this.name = name;
    }
}
