package com.task_management_service.backend.service;

import com.task_management_service.backend.dto.request.CreateTaskDto;
import com.task_management_service.backend.dto.request.RemoveTaskDto;
import com.task_management_service.backend.dto.request.UpdateTaskDto;
import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.enumeration.TaskStatus;
import com.task_management_service.backend.helper.TaskServiceHelper;
import com.task_management_service.backend.repository.TaskRepository;
import com.task_management_service.backend.utils.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskServiceHelper taskServiceHelper;

    public TaskEntity create(CreateTaskDto createTaskDto) {
        TaskEntity task = new TaskEntity();
        task.setTitle(createTaskDto.title());
        task.setDescription(createTaskDto.description());
        task.setUntilDate(createTaskDto.untilDate());
        task.setStatus(TaskStatus.TODO);

        UserEntity currentUser = userService.getCurrentUser();
        currentUser.getTasks().add(task);
        task.setUser(currentUser);

        taskRepository.save(task);

        return task;
    }

    public ArrayList<TaskEntity> getAll() {
        UserEntity currentUser = userService.getCurrentUser();

        return new ArrayList<>(currentUser.getTasks());
    }

    public void remove(RemoveTaskDto removeTaskDto) {
        UUID taskId = Converter.convertStringToUUID(removeTaskDto.taskId());
        taskServiceHelper.findTaskAndMakeUserVerification(taskId);

        taskRepository.deleteById(taskId);
    }

    public TaskEntity update(UpdateTaskDto updateTaskDto) {
        UUID taskId = Converter.convertStringToUUID(updateTaskDto.taskId());
        TaskEntity task = taskServiceHelper.findTaskAndMakeUserVerification(taskId);

        task.setTitle(updateTaskDto.title());
        task.setDescription(updateTaskDto.description());
        task.setUntilDate(updateTaskDto.untilDate());
        task.setStatus(updateTaskDto.status());

        taskRepository.save(task);

        return task;
    }


}
