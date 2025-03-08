package com.kb.service;

import com.kb.Entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
    Optional<User> getUserById(Long userId);
    List<User> getAllUsers();
    User getUserByEmail(String email);
    Optional<User> getUserByContact(String contact);
    Optional<User> authenticateUser(String email, String password);
}
