package com.task_management_service.backend.service;

import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getUserByUsernameOrEmail(String username, String email) {
        return userRepository.findUserEntityByUsernameOrEmail(username, email)
                .orElse(null);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findUserEntityByEmail(email)
                .orElse(null);
    }

    public UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return getUserByEmail(email);
    }

    public void saveUserToDb(UserEntity user) {
        userRepository.save(user);
    }
}
