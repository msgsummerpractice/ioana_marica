package com.example.spring_project;

import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepository;
import com.example.spring_project.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        UserRequest request = new UserRequest(
                1,
                "John123",
                "password123",
                "john@example.com",
                "John",
                "Doe");

        User savedUser = new User(
                1,
                "John123",
                "password123",
                "john@example.com",
                "John",
                "Doe");

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(savedUser);

        UserResponse response = userService.saveEntity(request);
        assertEquals("John123", response.getUsername());
        assertEquals("john@example.com", response.getEmail());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void testUpdateUser() {

        User user = new User(
                1,
                "John123",
                "password123",
                "john@example.com",
                "John",
                "Doe");

        UserRequest request = new UserRequest(
                1,
                "John123",
                "password123",
                "john@example.com",
                "John",
                "Doe");

        Mockito.when(userRepository.findById(1))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);
        UserResponse response = userService.updateEntity(request);
        assertEquals("John123", response.getUsername());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void testDeleteUser() {
        UserRequest request = new UserRequest();
        request.setId(1);

        Mockito.when(userRepository.existsById(1)).thenReturn(true);
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
                .thenReturn(Optional.of(user)); 
    }


    @Test
    void testFindTop10ByOrderByUsernameAsc() {

        List<User> users = Arrays.asList(
                new User(1, "Alice", "pass1", "alice@example.com", "Alice", "Smith"),
                new User(2, "Bob", "pass2", "bob@example.com", "Bob", "Johnson"));

        Mockito.when(userRepository.findTop10ByOrderByUsernameAsc())
                .thenReturn(users);

        List<UserResponse> result = userService.findTop10ByOrderByUsernameAsc();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getUsername());
        assertEquals("Bob", result.get(1).getUsername());
    }

    @Test
    void testCountUsers() {

        Mockito.when(userRepository.countUsers()).thenReturn(5);

        int result = userService.countUsers();

        assertEquals(5, result);
    }
}
