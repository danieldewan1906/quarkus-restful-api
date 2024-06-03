package org.learn.model.auth;

public record RegisterResponse(
        Long id,
        String name,
        String email
) {
}
