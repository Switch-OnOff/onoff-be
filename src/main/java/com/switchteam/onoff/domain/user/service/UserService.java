package com.switchteam.onoff.domain.user.service;

import com.switchteam.onoff.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByEmail(String email);

    User saveUser(User user);

    List<User> getAllUsers();
}
