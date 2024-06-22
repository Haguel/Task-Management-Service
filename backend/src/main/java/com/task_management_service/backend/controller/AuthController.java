package com.task_management_service.backend.controller;

import com.task_management_service.backend.dto.request.SignInUserDto;
import com.task_management_service.backend.dto.request.SignUpUserDto;
import com.task_management_service.backend.dto.response.JwtAuthDto;
import com.task_management_service.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Sign up user and get json token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully signed up",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthDto.class)) }),
            @ApiResponse(responseCode = "409", description = "User with such username or email already exists",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data provided",
                    content = @Content) })
    public JwtAuthDto signUp(@Valid @RequestBody SignUpUserDto signUpUserDto) {
         return authService.signUp(signUpUserDto);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Sign in user and get json token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully signed in",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtAuthDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data provided",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Provided invalid email or password",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Can not find authenticated user by provided email",
                    content = @Content),
    })
    public JwtAuthDto signIn(@RequestBody @Valid SignInUserDto signInUserDto) {
        return authService.signIn(signInUserDto);
    }
}
