package com.task_management_service.backend.service;

import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.repository.UserRepository;
import com.task_management_service.backend.test_utils.TestValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void shouldReturnUserByUsernameOrEmail() {
        UserEntity user = TestValues.getUser();

        when(userRepository.findUserEntityByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(user));

        UserEntity returnedUser = userService.getUserByUsernameOrEmail(user.getUsername(), user.getEmail());

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(returnedUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(returnedUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(returnedUser.getPassword());

    }

    @Test
    void shouldSaveUserToDb() {
        UserEntity user = TestValues.getUser();

        ArgumentCaptor<UserEntity> userArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);

        userService.saveUserToDb(user);
        verify(userRepository).save(userArgumentCaptor.capture());

        UserEntity savedUser = userArgumentCaptor.getValue();

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(savedUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(savedUser.getPassword());
    }
}