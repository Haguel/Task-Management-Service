package com.task_management_service.backend.controller;

import com.task_management_service.backend.dto.request.CreateTaskDto;
import com.task_management_service.backend.dto.request.RemoveTaskDto;
import com.task_management_service.backend.dto.request.UpdateTaskDto;
import com.task_management_service.backend.dto.response.JwtAuthDto;
import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create and return task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created task",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data provided",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request provided without token",
                    content = @Content),
    })
    public TaskEntity create(@RequestBody @Valid CreateTaskDto createTaskDto) {
        return taskService.create(createTaskDto);
    }

    @GetMapping
    @Operation(summary = "Get all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned task",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaskEntity.class))) }),
            @ApiResponse(responseCode = "401", description = "Request provided without token",
                    content = @Content),
    })
    public ArrayList<TaskEntity> getAll() {
        return taskService.getAll();
    }

    @DeleteMapping
    @Operation(summary = "Remove task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed task",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data provided",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request provided without token",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "User can't manage tasks of another user",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task with provided id wasn't found",
                    content = @Content),
    })
    public void remove(@RequestBody @Valid RemoveTaskDto removeTaskDto) {
        taskService.remove(removeTaskDto);
    }

    @PutMapping
    @Operation(summary = "Update task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated task",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data provided",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Request provided without token",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "User can't manage tasks of another user",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task with provided id wasn't found",
                    content = @Content),
    })
    public TaskEntity update(@RequestBody @Valid UpdateTaskDto updateTaskDto) {
        return taskService.update(updateTaskDto);
    }
}
