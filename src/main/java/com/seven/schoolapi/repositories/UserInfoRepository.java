package com.seven.schoolapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.seven.schoolapi.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);

    List<UserInfo> findByRoles(String roles);

    Optional<UserInfo> findById(long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserInfo> findByEmail(String email);

    Optional<UserInfo> findById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE UserInfo u SET u.password = :newPassword WHERE u.email = :email")
    void updatePassword(String email, String newPassword);
}
