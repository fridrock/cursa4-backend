package ru.fridrock.jir_backend.tasks.dto;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import ru.fridrock.jir_backend.tasks.enums.TaskPriority;
import ru.fridrock.jir_backend.tasks.enums.TaskSource;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TaskDto(
        UUID taskId,
        UUID dashboardId,
        String title,
        String description,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime deadline,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime issued,
        TaskSource source,
        TaskPriority priority,
        Integer hoursSpent) {
}
