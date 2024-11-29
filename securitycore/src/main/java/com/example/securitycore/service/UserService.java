package com.example.securitycore.service;

import com.example.securitycore.entities.User;
import com.example.securitycore.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface UserService {
    void createUser(String username, String password);
    Optional<User> findByUsername(String username);
    void deleteUser(Long userId);
    void blockUser(Long userId);
    void undeleteUser(Long userId);
    void unblockUser(Long userId);
}