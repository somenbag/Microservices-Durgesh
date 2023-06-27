package com.demo.user.service.sevices;

import com.demo.user.service.entities.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUser();

    User getUserById(String id);
}
