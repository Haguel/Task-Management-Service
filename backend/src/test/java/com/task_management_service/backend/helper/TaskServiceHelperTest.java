package com.task_management_service.backend.helper;

import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.repository.TaskRepository;
import com.task_management_service.backend.service.UserService;
import com.task_management_service.backend.test_utils.TestValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceHelperTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private TaskServiceHelper taskServiceHelper;

    @BeforeEach
    void setUp() {
        reset(taskRepository, userService);
    }

    @Test
    void shouldFindTaskAndMakeUserVerification() {
        UUID userId = UUID.randomUUID();
        TaskEntity testTask = TestValues.getTask();
        UserEntity testUser = TestValues.getUser();
        testUser.getTasks().add(testTask);
        testTask.setUser(testUser);
        testUser.setId(userId);

        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.of(testTask));
        when(userService.getCurrentUser()).thenReturn(testUser);

        assertAll(() -> taskServiceHelper.findTaskAndMakeUserVerification(UUID.randomUUID()));
    }

    @Test
    void shouldThrow404WhenUnableToFindTask() {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        try {
            taskServiceHelper.findTaskAndMakeUserVerification(UUID.randomUUID());
        } catch (ResponseStatusException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Test
    void shouldThrow401WhenUnauthorizedUser() {
        UserEntity unauthorizedUser = TestValues.getUser(2);
        UserEntity taskUser = TestValues.getUser(1);
        TaskEntity testTask = TestValues.getTask();
        taskUser.getTasks().add(testTask);
        testTask.setUser(taskUser);
        taskUser.setId(UUID.randomUUID());
        unauthorizedUser.setId(UUID.randomUUID());

        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.of(testTask));
        when(userService.getCurrentUser()).thenReturn(unauthorizedUser);

        try {
            taskServiceHelper.findTaskAndMakeUserVerification(UUID.randomUUID());
        } catch (ResponseStatusException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }
}