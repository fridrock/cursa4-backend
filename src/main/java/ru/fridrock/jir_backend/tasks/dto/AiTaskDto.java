package ru.fridrock.jir_backend.tasks.dto;

public record AiTaskDto(String title,
                        String deadline,
                        String description,
                        String priority) {
}
