package com.task_management_service.backend.service;

import com.task_management_service.backend.dto.request.SignInUserDto;
import com.task_management_service.backend.dto.request.SignUpUserDto;
import com.task_management_service.backend.dto.response.JwtAuthDto;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.test_utils.TestValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userService, jwtService, passwordEncoder, authenticationManager);
    }

    @Test
    void shouldSignUpThenReturnJwtToken() {
        UserEntity user = TestValues.getUser();
        SignUpUserDto signUpUserDto = new SignUpUserDto(user.getName(), user.getUsername(), user.getEmail(), user.getPassword());
        String token = TestValues.getToken();
        String passwordHash = TestValues.getHash();

        when(jwtService.generateToken(any()))
                .thenReturn(token);
        when(passwordEncoder.encode(anyString()))
                .thenReturn(passwordHash);

        JwtAuthDto jwtAuthDto = authService.signUp(signUpUserDto);

        assertThat(jwtAuthDto).isNotNull();
        assertThat(jwtAuthDto.token()).isEqualTo(token);
    }

    @Test
    void shouldNotSignUpExistedUser() {
        UserEntity user = TestValues.getUser();
        SignUpUserDto signUpUserDto = new SignUpUserDto(user.getName(), user.getUsername(), user.getEmail(), user.getPassword());

        when(userService.getUserByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(user);

        assertThrows(ResponseStatusException.class, () -> authService.signUp(signUpUserDto));
    }

    @Test
    void shouldSignInAndReturnJwtToken() {
        UserEntity user = TestValues.getUser();
        SignInUserDto signInUserDto = new SignInUserDto(user.getEmail(), user.getPassword());
        String token = TestValues.getToken();

        when(userService.getUserByEmail(any()))
                .thenReturn(user);
        when(jwtService.generateToken(any()))
                .thenReturn(token);

        JwtAuthDto jwtAuthDto = authService.signIn(signInUserDto);

        assertThat(jwtAuthDto).isNotNull();
        assertThat(jwtAuthDto.token()).isEqualTo(token);
    }

    @Test
    void shouldNotSignInNonExistentUser() {
        UserEntity user = TestValues.getUser();
        SignInUserDto signInUserDto = new SignInUserDto(user.getEmail(), user.getPassword());

        when(userService.getUserByEmail(anyString()))
                .thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> authService.signIn(signInUserDto));
    }
}
