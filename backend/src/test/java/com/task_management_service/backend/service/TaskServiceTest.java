package com.task_management_service.backend.service;

import com.task_management_service.backend.dto.request.CreateTaskDto;
import com.task_management_service.backend.dto.request.RemoveTaskDto;
import com.task_management_service.backend.dto.request.UpdateTaskDto;
import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.helper.TaskServiceHelper;
import com.task_management_service.backend.repository.TaskRepository;
import com.task_management_service.backend.test_utils.TestValues;
import com.task_management_service.backend.utils.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserService userService;
    @Mock
    private TaskServiceHelper taskServiceHelper;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, userService, taskServiceHelper);
    }

    @Test
    void shouldCreateAndReturnTask() {
        UserEntity testUser = TestValues.getUser();
        TaskEntity testTask = TestValues.getTask();
        CreateTaskDto createTaskDto = new CreateTaskDto(
                testTask.getTitle(),
                testTask.getDescription(),
                Converter.convertToIso8601(testTask.getUntilDate())
        );

        when(userService.getCurrentUser()).thenReturn(testUser);

        TaskEntity createdTask = taskService.create(createTaskDto);

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTitle()).isEqualTo(testTask.getTitle());
        assertThat(createdTask.getStatus()).isEqualTo(testTask.getStatus());
        assertThat(createdTask.getUser().getName()).isEqualTo(testUser.getName());
    }

    @Test
    void shouldGetAllTasks() {
        UserEntity testUser = TestValues.getUser();
        testUser.getTasks().add(TestValues.getTask(1));
        testUser.getTasks().add(TestValues.getTask(2));
        testUser.getTasks().add(TestValues.getTask(3));

        when(userService.getCurrentUser()).thenReturn(testUser);

        ArrayList<TaskEntity> tasks = taskService.getAll();

        assertThat(tasks).isNotNull();
        assertThat(tasks.size()).isEqualTo(3);
    }

    @Test
    void shouldRemoveTask() throws Exception {
        TaskEntity taskToRemove = TestValues.getTask(1);
        RemoveTaskDto removeTaskDto = new RemoveTaskDto(
                UUID.randomUUID().toString()
        );

        when(taskServiceHelper.findTaskAndMakeUserVerification(any(UUID.class))).thenReturn(taskToRemove);

        assertAll(() -> taskService.remove(removeTaskDto));
    }

    @Test
    void shouldUpdateAndReturn() {
        TaskEntity taskToUpdate = TestValues.getTask(1);
        String updatedTitle = "Hello world";
        UpdateTaskDto updateTaskDto = new UpdateTaskDto(
                UUID.randomUUID().toString(),
                updatedTitle,
                taskToUpdate.getDescription(),
                Converter.convertToIso8601(taskToUpdate.getUntilDate()),
                taskToUpdate.getStatus()
        );

        when(taskServiceHelper.findTaskAndMakeUserVerification(any(UUID.class))).thenReturn(taskToUpdate);

        TaskEntity updatedTask = taskService.update(updateTaskDto);

        assertThat(updatedTask.getTitle()).isEqualTo(updatedTitle);
    }
}