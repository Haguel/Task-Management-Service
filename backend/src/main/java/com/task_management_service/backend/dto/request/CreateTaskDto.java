package com.task_management_service.backend.dto.request;

import com.task_management_service.backend.enumeration.TaskStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskDto(
        @NotNull
        String title,

        String description,

        @NotNull
        LocalDateTime untilDate,

        @NotNull
        TaskStatus status
) {
}
