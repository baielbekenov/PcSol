package com.example.securityapplication.controller;

import com.example.security.application.service.UserService;
import com.example.security.core.model.User;
import com.example.security.core.dto.UserDTO;
import com.example.generalapi.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Создание пользователя
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO.getUsername(), userDTO.getPassword());
            UserResponse response = new UserResponse("User created successfully", true, null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            UserResponse response = new UserResponse("Error creating user", false, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Поиск пользователя по имени
    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        try {
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                UserDTO userDTO = new UserDTO(user.getUsername(), user.getRoles());
                UserResponse response = new UserResponse("User found", true, userDTO);
                return ResponseEntity.ok(response);
            } else {
                UserResponse response = new UserResponse("User not found", false, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            UserResponse response = new UserResponse("Error fetching user", false, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Блокировка пользователя
    @PutMapping("/block/{username}")
    public ResponseEntity<UserResponse> blockUser(@PathVariable String username) {
        try {
            userService.blockUser(username);
            UserResponse response = new UserResponse("User blocked successfully", true, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            UserResponse response = new UserResponse("Error blocking user", false, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Удаление пользователя
    @DeleteMapping("/{username}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String username) {
        try {
            userService.deleteUser(username);
            UserResponse response = new UserResponse("User deleted successfully", true, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            UserResponse response = new UserResponse("Error deleting user", false, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Восстановление пользователя
    @PutMapping("/restore/{username}")
    public ResponseEntity<UserResponse> restoreUser(@PathVariable String username) {
        try {
            userService.restoreUser(username);
            UserResponse response = new UserResponse("User restored successfully", true, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            UserResponse response = new UserResponse("Error restoring user", false, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}