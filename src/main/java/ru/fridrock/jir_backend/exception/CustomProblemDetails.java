package ru.fridrock.jir_backend.exception;

import lombok.Builder;

@Builder
public record CustomProblemDetails(String title, int status, String details, String path) {

}
