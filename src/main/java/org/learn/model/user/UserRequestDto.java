package org.learn.model.user;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword
) {
}
