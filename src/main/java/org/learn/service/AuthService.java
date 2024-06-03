package org.learn.service;

import jakarta.ws.rs.core.SecurityContext;
import org.learn.model.auth.LoginRequest;
import org.learn.model.auth.LoginResponse;
import org.learn.model.auth.RegisterRequest;
import org.learn.model.auth.RegisterResponse;

public interface AuthService {

    RegisterResponse registerUser(RegisterRequest request);
    LoginResponse loginUser(LoginRequest request);
    void logoutUser(SecurityContext sc);
}
