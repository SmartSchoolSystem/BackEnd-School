package com.seven.schoolapi.repositories;

import java.util.Optional;

import com.seven.schoolapi.entity.Class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    Optional<Class> findByClassRoom(String classRoom);

    Optional<Class> findByIdClass(Long idClass);

    void deleteById(Long idClass);

    boolean existsById(Long idClass);
}