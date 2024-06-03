package org.learn.service;

import org.learn.model.user.UserDto;

public interface UserService {

    UserDto getUserByEmail(String email);
}
