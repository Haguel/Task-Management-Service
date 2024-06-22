package com.task_management_service.backend.service;

import com.task_management_service.backend.dto.request.CreateTaskDto;
import com.task_management_service.backend.dto.request.RemoveTaskDto;
import com.task_management_service.backend.dto.request.UpdateTaskDto;
import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.repository.TaskRepository;
import com.task_management_service.backend.utils.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskEntity create(CreateTaskDto createTaskDto) {
        TaskEntity task = new TaskEntity();
        task.setTitle(createTaskDto.title());
        task.setDescription(createTaskDto.description());
        task.setUntilDate(createTaskDto.untilDate());
        task.setStatus(createTaskDto.status());

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
        TaskEntity task = findTaskAndMakeUserVerification(taskId);

        task.getUser().getTasks().remove(this);
        taskRepository.deleteById(taskId);
    }

    public TaskEntity update(UpdateTaskDto updateTaskDto) {
        UUID taskId = Converter.convertStringToUUID(updateTaskDto.taskId());
        TaskEntity task = findTaskAndMakeUserVerification(taskId);

        task.setTitle(updateTaskDto.title());
        task.setDescription(updateTaskDto.description());
        task.setUntilDate(updateTaskDto.untilDate());
        task.setStatus(updateTaskDto.status());

        taskRepository.save(task);

        return task;
    }

    private TaskEntity findTaskAndMakeUserVerification(UUID id) {
        TaskEntity task = taskRepository.findById(id).orElse(null);

        if(task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with provided id wasn't found");
        }

        UserEntity user = userService.getCurrentUser();

        if(!task.getUser().getId().equals(user.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User can't manage tasks of another user");
        }

        return task;
    }
}
