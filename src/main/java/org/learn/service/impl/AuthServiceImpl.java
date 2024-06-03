package org.learn.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.SecurityContext;
import org.learn.core.StringUtils;
import org.learn.core.TokenUtils;
import org.learn.entity.User;
import org.learn.exception.ResourceNotFoundExceptions;
import org.learn.model.auth.LoginRequest;
import org.learn.model.auth.LoginResponse;
import org.learn.model.auth.RegisterRequest;
import org.learn.model.auth.RegisterResponse;
import org.learn.repository.UserRepository;
import org.learn.service.AuthService;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private TokenUtils tokenUtils;

    @Override
    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {
        User checkUser = userRepository.findByEmail(request.email()).firstResult();
        if (checkUser != null) {
            throw new RuntimeException("User Already Exists!");
        }

        String hashPassword = StringUtils.base64Encode(request.password());
        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setRole("user");
        user.setPassword(hashPassword);

        userRepository.persist(user);
        return new RegisterResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    @Transactional
    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).firstResult();
        if (user == null) {
            throw new ResourceNotFoundExceptions("Email or Password Wrong");
        }

        String decodePassword = StringUtils.base64Decode(user.getPassword());
        if (!decodePassword.equals(request.password())) {
            throw new ResourceNotFoundExceptions("Email or Password Wrong");
        }

        String token = tokenUtils.generateToken(request);
        user.setToken(token);
        userRepository.persist(user);
        return new LoginResponse(user.getEmail(), token);
    }

    @Override
    @Transactional
    public void logoutUser(SecurityContext sc) {
        String email = sc.getUserPrincipal().getName();
        User user = userRepository.findByEmail(email).firstResult();
        user.setToken(null);
        userRepository.persist(user);
    }
}
