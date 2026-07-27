package com.example.spring_project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.service.UserServiceImpl;

import jakarta.validation.Valid;

import com.example.spring_project.dto.request.DeleteUserRequest;
import com.example.spring_project.dto.request.UpdateUserRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        logger.info("Fetching all users");
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest request) {
        logger.info("Adding user {}", request.getUsername());
        UserResponse response = userService.saveEntity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @Valid @RequestBody DeleteUserRequest request) {

        try {
            userService.deleteEntityByID(request);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(
            @Valid @RequestBody UserRequest request) {

        UserResponse response = userService.updateEntity(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.ok(userService.getById(request));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = "email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.ok(userService.getByEmail(request));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = "username")
    public ResponseEntity<UserResponse> getUserByUsername(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.ok(userService.getByUsername(request));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/top10")
    public ResponseEntity<List<UserResponse>> getTop10Users() {
        return ResponseEntity.ok(userService.findTop10ByOrderByUsernameAsc());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countUsers() {
        return ResponseEntity.ok(userService.countUsers());
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<UserResponse> updateEmailById(@Valid @RequestBody UpdateUserRequest request) {

        try {
            return ResponseEntity.ok(userService.updateEmailById(request));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Value("${api.version}")
    private String apiVersion;

    @GetMapping("/info")
    public String getApiVersion() {
        return "API Version: " + apiVersion;
    }

    @Value("${app.welcome.message}")
    private String welcomeMessage;

    @GetMapping("/welcome")
    public String getWelcomeMessage() {
        return welcomeMessage;
    }
}
