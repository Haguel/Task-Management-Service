package com.task_management_service.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record SignUpUserDto(
        @NotNull(message = "Name field can't be null")
        String name,

        @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Invalid username. It should contain only English letters and numbers")
        @NotNull(message = "Username field can't be null")
        String username,

        @Email(message = "Invalid email. Please provide correct email address")
        @NotNull(message = "Email field can't be null")
        String email,

        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
        @NotNull(message = "Password field can't be null")
        String password
){
}
