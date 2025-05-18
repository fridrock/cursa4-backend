package ru.fridrock.jir_backend.utils.jwt;

import java.util.UUID;

public record UserToken(String username, UUID userId) {
}
