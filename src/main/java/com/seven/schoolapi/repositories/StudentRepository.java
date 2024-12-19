package com.seven.schoolapi.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seven.schoolapi.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByEnrollment(String enrollment);

    Optional<Student> findByName(String name);

    List<Student> findAllByEnrollment(String enrollment);

    boolean existsByEnrollment(String enrollment);

    void deleteByEnrollment(String enrollment);
}
