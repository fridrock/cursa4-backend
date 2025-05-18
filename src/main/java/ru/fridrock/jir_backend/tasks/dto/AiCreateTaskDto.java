package ru.fridrock.jir_backend.tasks.dto;

import java.util.UUID;

public record AiCreateTaskDto(UUID dashboardId,
                              String message) {
}
