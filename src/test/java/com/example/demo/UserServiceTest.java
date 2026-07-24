package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

public class UserServiceTest {

    private UserRepository mockRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(mockRepository);
    }

    @Test
    void testCountUsers() {
        List<User> mockUsers = Arrays.asList(new User(1, "John", 30), new User(2, "Jane", 25), new User(3, "Doe", 40));
        Mockito.when(mockRepository.findAll()).thenReturn(mockUsers);
        int count = userService.count();
        assertEquals(3, count);
    }

}
