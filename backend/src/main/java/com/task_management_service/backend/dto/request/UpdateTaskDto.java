package com.task_management_service.backend.dto.request;

import com.task_management_service.backend.enumeration.TaskStatus;
import com.task_management_service.backend.utils.RegexpBase;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record UpdateTaskDto(
    @Pattern(regexp = RegexpBase.UUID_REGEXP, message = "Task id must be a type of UUID")
    @NotNull
    String taskId,

    @NotNull
    String title,

    String description,

    @NotNull
    @Pattern(regexp = RegexpBase.ISO8601_REGEXP, message = "untilDate must be in ISO 8601 format")
    String untilDate,

    @NotNull
    TaskStatus status
) {
}
