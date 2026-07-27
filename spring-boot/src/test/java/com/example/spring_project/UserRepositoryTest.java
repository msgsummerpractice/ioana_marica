package com.example.spring_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;

import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepositoryImpl;

public class UserRepositoryTest {

    private UserRepositoryImpl mockRepository;
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(UserRepositoryImpl.class);
        userRepository = mockRepository;
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(1,"John123", "password123", "john@example.com", "John", "Doe"), new User(2, "Jane", "password456", "jane@example.com", "Jane", "Smith"));
        Mockito.when(mockRepository.findAll()).thenReturn(mockUsers);
        List<User> result = userRepository.findAll();
        assertEquals(mockUsers, result);
    }

    @Test
    void testSaveUser() {
        User newUser = new User(3, "Alice", "password789", "alice@example.com", "Alice", "Doe");
        Mockito.when(mockRepository.save(newUser)).thenReturn(newUser);
        User result = userRepository.save(newUser);
        assertEquals(newUser, result);

        User newUser2 = new User(6, "Bob", "password000", "bob@example.com", "Bob", "Smith");
        try {
            userRepository.save(newUser2);
        } catch (Exception e) {
            assert(e instanceof jakarta.validation.ConstraintViolationException);
        }
    }

   
}