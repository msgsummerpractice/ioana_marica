package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

public class UserRepositoryTest {

    private UserRepository mockRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(UserRepository.class);
        userRepository = mockRepository;
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(new User("John"), new User("Jane"));
        Mockito.when(mockRepository.findAll()).thenReturn(mockUsers);
        List<User> result = userRepository.findAll();
        assertEquals(mockUsers, result);
    }
}