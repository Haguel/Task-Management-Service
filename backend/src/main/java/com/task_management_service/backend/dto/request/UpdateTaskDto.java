package com.task_management_service.backend.dto.request;

import com.task_management_service.backend.enumeration.TaskStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record UpdateTaskDto(
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}", message = "Task id must be a type of UUID")
    @NotNull
    String taskId,

    @NotNull
    String title,

    String description,

    @NotNull
    LocalDateTime untilDate,

    @NotNull
    TaskStatus status
) {
}
