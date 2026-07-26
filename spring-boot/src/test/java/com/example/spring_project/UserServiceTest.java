package com.example.spring_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.spring_project.repository.UserRepositoryImpl;
import com.example.spring_project.service.UserServiceImpl;

public class UserServiceTest {
    private UserRepositoryImpl userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepositoryImpl.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testGetAllUsers() {
        userService.getAll();
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void testSaveUser() {
        userService.saveEntity(Mockito.mock(com.example.spring_project.model.User.class));
        Mockito.verify(userRepository).save(Mockito.any(com.example.spring_project.model.User.class));
    }
}
