package com.task_management_service.backend.dto.request;

import jakarta.validation.constraints.NotNull;

public record GetUserDto(
        @NotNull(message = "username can't be null")
        String username
) {
}
