package com.task_management_service.backend.helper;

import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.repository.TaskRepository;
import com.task_management_service.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskServiceHelper {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskEntity findTaskAndMakeUserVerification(UUID taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElse(null);

        if(task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with provided id wasn't found");
        }

        UserEntity user = userService.getCurrentUser();

        if(!task.getUser().getId().equals(user.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User can't manage tasks of another user");
        }

        return task;
    }
}
