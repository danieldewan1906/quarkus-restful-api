package org.learn.model.auth;

public record LoginResponse(
        String email,
        String token
) {
}
