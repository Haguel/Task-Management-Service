package com.task_management_service.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignInUserDto{
        @NotNull(message = "Email field can't be null.")
        @Email(message = "Invalid email. Please provide correct email address")
        final String email;

        @NotNull(message = "Password field can't be null.")
        final String password;
}
