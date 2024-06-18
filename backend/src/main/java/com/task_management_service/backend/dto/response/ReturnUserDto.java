package com.task_management_service.backend.dto.response;

import com.task_management_service.backend.enumeration.Role;

import java.util.UUID;

public record ReturnUserDto(
        UUID id,
        String name,
        String username,
        String email,
        Role role
) {
}
