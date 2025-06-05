package ru.fridrock.jir_backend.tasks.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AiCreateTaskDto(UUID dashboardId,
                              @NotNull
                              @NotEmpty
                              String message) {
}
