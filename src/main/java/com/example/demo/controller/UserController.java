package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.User;
import java.util.List;
import com.example.demo.service.UserService;

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
    @PostMapping("/add")
    public User addUser(@Valid @RequestBody User user) {
        logger.info("Adding a new user: {} {} {}", user.getId(), user.getName(), user.getAge());
        return userService.saveEntity(user);
    }

}
