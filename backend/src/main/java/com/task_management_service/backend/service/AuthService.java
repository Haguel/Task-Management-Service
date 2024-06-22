package com.task_management_service.backend.service;

import com.task_management_service.backend.dto.request.SignInUserDto;
import com.task_management_service.backend.dto.request.SignUpUserDto;
import com.task_management_service.backend.dto.response.JwtAuthDto;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.enumeration.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthDto signUp(SignUpUserDto signUpUserDto) {
        UserEntity existedUser = userService.getUserByUsernameOrEmail(signUpUserDto.username(), signUpUserDto.email());

        if(existedUser != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with such username or email already exists");
        }

        UserEntity newUser = new UserEntity(
                signUpUserDto.name(),
                signUpUserDto.username(),
                signUpUserDto.email(),
                passwordEncoder.encode(signUpUserDto.password()),
                Role.USER
        );

        userService.saveUserToDb(newUser);

        return new JwtAuthDto(jwtService.generateToken(newUser));
    }

    public JwtAuthDto signIn(SignInUserDto signInUserDto) {
        UserEntity user = userService.getUserByEmail(signInUserDto.email());

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Can not find authenticated user by provided email");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInUserDto.email(),
                signInUserDto.password()
        ));

        return new JwtAuthDto(jwtService.generateToken(user));
    }
}
