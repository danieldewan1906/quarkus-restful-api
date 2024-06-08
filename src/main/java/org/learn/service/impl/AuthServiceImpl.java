package org.learn.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.SecurityContext;
import org.learn.core.StringUtils;
import org.learn.core.TokenUtils;
import org.learn.entity.User;
import org.learn.model.auth.LoginRequest;
import org.learn.model.auth.LoginResponse;
import org.learn.model.auth.RegisterRequest;
import org.learn.model.auth.RegisterResponse;
import org.learn.repository.UserRepository;
import org.learn.service.AuthService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private TokenUtils tokenUtils;

    private static final String patternEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    @Override
    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {
        User checkUser = userRepository.findByEmail(request.email()).firstResult();
        if (checkUser != null) {
            throw new WebApplicationException("User Already Exists!", 500);
        }

        if (!isValidEmail(request.email())) {
            throw new WebApplicationException("Email Invalid", 500);
        }

        String hashPassword = StringUtils.base64Encode(request.password());
        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());

        String role = request.role() != null ? request.role() : "user";
        user.setRole(role);
        user.setPassword(hashPassword);

        userRepository.persist(user);
        return new RegisterResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    @Transactional
    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).firstResult();
        if (user == null) {
            throw new WebApplicationException("Email or Password Wrong", 404);
        }

        String decodePassword = StringUtils.base64Decode(user.getPassword());
        if (!decodePassword.equals(request.password())) {
            throw new WebApplicationException("Email or Password Wrong", 404);
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
        if (user == null) {
            throw new WebApplicationException("User Not Found", 404);
        }

        user.setToken(null);
        userRepository.persist(user);
    }

    private Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(patternEmail, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
