package com.task_management_service.backend.controller;

import com.task_management_service.backend.dto.request.CreateTaskDto;
import com.task_management_service.backend.dto.request.RemoveTaskDto;
import com.task_management_service.backend.dto.request.UpdateTaskDto;
import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskEntity create(@RequestBody @Valid CreateTaskDto createTaskDto) {
        return taskService.create(createTaskDto);
    }

    @GetMapping
    public ArrayList<TaskEntity> getAll() {
        return taskService.getAll();
    }

    @DeleteMapping
    public void remove(@RequestBody @Valid RemoveTaskDto removeTaskDto) {
        taskService.remove(removeTaskDto);
    }

    @PutMapping
    public TaskEntity update(@RequestBody @Valid UpdateTaskDto updateTaskDto) {
        return taskService.update(updateTaskDto);
    }
}
