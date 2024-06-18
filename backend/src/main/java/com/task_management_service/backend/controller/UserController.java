package com.task_management_service.backend.controller;

import com.task_management_service.backend.dto.response.ReturnUserDto;
import com.task_management_service.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get user by his username or email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReturnUserDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Request provided without token",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User with provided username or email wasn't found",
                    content = @Content) })
    public ReturnUserDto getUser(@PathVariable String username) {
        return userService.getUser(username, username);
    }
}
