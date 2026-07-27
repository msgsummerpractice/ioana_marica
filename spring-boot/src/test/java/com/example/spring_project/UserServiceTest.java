package com.example.spring_project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.spring_project.repository.UserRepository;
import com.example.spring_project.service.UserServiceImpl;
import com.example.spring_project.model.User;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
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

    @Test
    void testUpdateUser() {
        User user = new User(1, "John123", "password123", "john@example.com", "John", "Doe");
        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));
        userService.updateEntity(user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void testDeleteUser() {
        Mockito.when(userRepository.existsById(1))
                .thenReturn(true);

        userService.deleteEntityByID(1);

        Mockito.verify(userRepository).existsById(1);
        Mockito.verify(userRepository).deleteById(1);
    }

    @Test
    void testGetUserById() {
        User user = new User(
                1,
                "John123",
                "password123",
                "john@example.com",
                "John",
                "Doe");

        Mockito.when(userRepository.findById(1))
                .thenReturn(java.util.Optional.of(user));
        User result = userService.getById(1);
        assertEquals(user, result);
        Mockito.verify(userRepository).findById(1);
    }

    @Test
    void testGetUserByEmail() {
        User user = new User(
                1,
                "John123",
                "password123",
                "john@example.com",
                "John",
                "Doe");
        Mockito.when(userRepository.getByEmail("john@example.com"))
                .thenReturn(user);
        User result = userService.getByEmail("john@example.com");
        assertEquals(user, result);
        Mockito.verify(userRepository)
                .getByEmail("john@example.com");
    }
}
