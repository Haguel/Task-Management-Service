package com.task_management_service.backend.repository;

import com.task_management_service.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityByUsernameOrEmail(String username, String email);
    Optional<UserEntity> findUserEntityByEmail(String email);
}
