package org.learn.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.learn.entity.User;
import org.learn.model.user.UserDto;
import org.learn.repository.UserRepository;
import org.learn.service.UserService;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).firstResultOptional().orElseThrow(() -> new NotFoundException());
        return new UserDto(user.getName(), user.getEmail(), user.getRole());
    }
}
