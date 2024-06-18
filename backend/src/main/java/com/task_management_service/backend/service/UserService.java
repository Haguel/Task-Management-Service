package com.task_management_service.backend.service;

import com.task_management_service.backend.dto.response.ReturnUserDto;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public ReturnUserDto getUser(String username, String email) {
        UserEntity user = getUserByUsernameOrEmail(username, email);

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with provided username or email wasn't found");
        }

        ReturnUserDto returnUserDto = new ReturnUserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );

        return returnUserDto;
    }

    public UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return getUserByEmail(email);
    }

    public void saveUserToDb(UserEntity user) {
        userRepository.save(user);
    }
}
