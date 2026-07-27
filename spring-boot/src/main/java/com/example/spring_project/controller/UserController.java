package com.example.spring_project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_project.model.User;
import com.example.spring_project.service.UserServiceImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        logger.info("UserController initialized");
        this.userService = userService;
    }

    // show all users
    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userService.getAll();
    }

    // add user
    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        logger.info("Adding a new user: {} {} {} {} {} {}", user.getId(), user.getUsername(),user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName());
        return ResponseEntity.ok(userService.saveEntity(user));
    }

    // delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            userService.deleteEntityByID(id);
            logger.info("Deleted user with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@Valid
            @PathVariable int id,
            @RequestBody User user) {

        user.setId(id);
        User updatedUser = userService.updateEntity(user);
        logger.info("Updated user with id: {} to {} {} {} {} {}", id, updatedUser.getUsername(), updatedUser.getPassword(),updatedUser.getEmail(), updatedUser.getFirstName(), updatedUser.getLastName());
        return ResponseEntity.ok(updatedUser);
    }

    // get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        try {
            User user = userService.getById(id);
            logger.info("Fetched user with id: {} {} {} {} {} {}", user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName());
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // get user by email
    @GetMapping(params = "email")
    public User getUserByEmail(@RequestParam String email) {
        logger.info("Fetching user with email: {}", email);
        return userService.getByEmail(email);
    }

    @GetMapping(params = "username")
    public User getUserByUsername(@RequestParam String username) {
        logger.info("Fetching user with username: {}", username);
        return userService.getByUsername(username);
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
