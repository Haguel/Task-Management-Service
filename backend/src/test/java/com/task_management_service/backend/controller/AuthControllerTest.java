package com.task_management_service.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_management_service.backend.dto.request.SignInUserDto;
import com.task_management_service.backend.dto.request.SignUpUserDto;
import com.task_management_service.backend.dto.response.JwtAuthDto;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.service.AuthService;
import com.task_management_service.backend.test_utils.TestValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(AuthController.class)
//@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {
    @Mock
    private AuthService authService;
    @InjectMocks
    private AuthController authController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldSignUpAndReturnToken() throws Exception {
        String token = TestValues.getSigningToken();
        UserEntity user = TestValues.getUser();
        SignUpUserDto signUpUserDto = new SignUpUserDto(user.getName(), "mike1999", user.getEmail(), user.getPassword());

        when(authService.signUp(any()))
                .thenReturn(new JwtAuthDto(token));

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void shouldNotSignUpWithInvalidBody() throws Exception {
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

        record WrongSignUpUserDto(String name, String country, boolean trueOrFalse) {}
        WrongSignUpUserDto wrongSignUpUserDto = new WrongSignUpUserDto("Mike", "USA", true);

        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongSignUpUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSignInAndReturnToken() throws Exception {
        String token = TestValues.getSigningToken();
        UserEntity user = TestValues.getUser();
        SignInUserDto signInUserDto = new SignInUserDto(user.getEmail(), user.getPassword());

        when(authService.signIn(any()))
                .thenReturn(new JwtAuthDto(token));

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void shouldNotSignInWithInvalidBody() throws Exception {
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

        record WrongSignInUserDto(String name, String country, boolean trueOrFalse) {}
        WrongSignInUserDto wrongSignInUserDto = new WrongSignInUserDto("Mike", "USA", true);

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongSignInUserDto)))
                .andExpect(status().isBadRequest());
    }
}