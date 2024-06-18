package com.task_management_service.backend.controller;

import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public UserEntity getUser(@PathVariable String username) {
        return userService.getUserByUsernameOrEmail(username, username);
    }
}
