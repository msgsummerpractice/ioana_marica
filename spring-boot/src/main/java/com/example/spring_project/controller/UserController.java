package com.example.spring_project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_project.model.User;
import com.example.spring_project.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        logger.info("UserController initialized");
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userService.getAll();
    }

    @Value("${user.min-age}")
    private int minAge;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        if (user.getAge() < minAge) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        logger.info("Adding a new user: {} {} {}", user.getId(), user.getName(), user.getAge());
        return ResponseEntity.ok(userService.saveEntity(user));
    }

}
