package com.example.spring_project.controller;

import com.example.spring_project.dto.request.UpdateUserRequest;
import com.example.spring_project.dto.request.UserRequest;
import org.springframework.http.MediaType;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.mapper.UserMapper;
import com.example.spring_project.model.User;
import com.example.spring_project.service.UserServiceImpl;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserServiceImpl userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        logger.info("Fetching all users");
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest request) {
        logger.info("Adding user {}", request.getUsername());

        User user = userMapper.toEntity(request);

        UserResponse response = userService.saveEntity(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            logger.info("Deleting user with ID {}", id);
            userService.deleteEntityByID(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}",
                consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable int id,
            @Valid @RequestBody UserRequest request) {

        logger.info("Updating user with ID {}", id);

        User user = userMapper.toEntity(request);

        UserResponse updatedUser = userService.updateEntity(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        try {
            logger.info("Fetching user with ID {}", id);
            return ResponseEntity.ok(userService.getById(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/email", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        try {
            logger.info("Fetching user with email {}", email);
            return ResponseEntity.ok(userService.getByEmail(email));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/username", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponse> getUserByUsername(@RequestParam String username) {
        try {
            logger.info("Fetching user with username {}", username);
            return ResponseEntity.ok(userService.getByUsername(username));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String username) {
        logger.info("Searching for users with username containing '{}'", username);
        return ResponseEntity.ok(userService.findTop10ByOrderByUsernameAsc(username));
    }

    @GetMapping(value = "/count", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Integer> countUsers() {
        return ResponseEntity.ok(userService.countUsers());
    }

    @PatchMapping(value = "/id/email", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponse> updateEmailById(
            @PathVariable int id,
            @Valid @RequestBody UpdateUserRequest request) {

        try {
            logger.info("Updating email for user with ID {}", id);
            User user = new User();
            user.setEmail(request.getEmail());
            UserResponse updatedUser = userService.updateEmailById(id, user);
            return ResponseEntity.ok(updatedUser);
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
