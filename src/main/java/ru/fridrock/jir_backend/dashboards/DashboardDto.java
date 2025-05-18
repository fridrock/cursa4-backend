package ru.fridrock.jir_backend.dashboards;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record DashboardDto(
    @NotBlank
    String name,
    UUID dashboardId) {
}
