package com.seven.schoolapi.repositories;

import org.springframework.stereotype.Repository;

import com.seven.schoolapi.entity.ActivityStudent;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ActivityStudentRepository extends JpaRepository<ActivityStudent, Long> {
    Optional<ActivityStudent> findById(Long id);
}