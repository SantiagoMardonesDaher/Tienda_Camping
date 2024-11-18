package com.uade.tpo.demo.service;

import java.util.List;

import com.uade.tpo.demo.entity.User;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Long id);
    User saveUser(User user);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    List<User> getAllUsers();
}
