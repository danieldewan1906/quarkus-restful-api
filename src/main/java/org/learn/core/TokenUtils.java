package org.learn.core;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.learn.model.auth.LoginRequest;
import org.learn.model.user.UserDto;
import org.learn.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class TokenUtils {

    @Inject
    private UserService userService;

    public String generateToken(LoginRequest request) {
        UserDto user = userService.getUserByEmail(request.email());
        Set<String> roles = new HashSet<>(
                Arrays.asList(user.role())
        );

        long duration = System.currentTimeMillis() + 3600;
        return Jwt.issuer(user.name())
                .subject(user.email())
                .groups(roles)
                .expiresAt(duration)
                .sign();
    }
}
