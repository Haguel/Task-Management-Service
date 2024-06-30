package com.task_management_service.backend.dto.request;

import com.task_management_service.backend.utils.RegexpBase;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateTaskDto(
        @NotNull
        String title,

        String description,

        @NotNull
        @Pattern(regexp = RegexpBase.ISO8601_REGEXP, message = "untilDate must be in ISO 8601 format")
        String untilDate
) {
}
