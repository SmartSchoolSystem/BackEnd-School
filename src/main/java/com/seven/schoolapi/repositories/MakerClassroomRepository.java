package com.seven.schoolapi.repositories;

import com.seven.schoolapi.entity.MakerClassroom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerClassroomRepository extends JpaRepository<MakerClassroom, Long> {
    Optional<MakerClassroom> findClassroomById(Long id);

    Optional<MakerClassroom> findClassroomByName(String name);

    boolean existsById(Long id);

    boolean existsByName(String name);

    boolean deleteByName(String name);
}
