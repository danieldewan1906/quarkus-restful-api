package org.learn.model.user;

public record UserDto(
        String name,
        String email,
        String role
) {
}
