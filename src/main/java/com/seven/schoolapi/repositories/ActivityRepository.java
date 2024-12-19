package com.seven.schoolapi.repositories;

import java.util.Optional;

import com.seven.schoolapi.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findById(Long id);
}