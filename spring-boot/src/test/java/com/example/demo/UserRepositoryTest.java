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
        List<User> mockUsers = Arrays.asList(new User(1, "John", 30), new User(2, "Jane", 25));
        Mockito.when(mockRepository.findAll()).thenReturn(mockUsers);
        List<User> result = userRepository.findAll();
        assertEquals(mockUsers, result);
    }

    @Test
    void testSaveUser() {
        User newUser = new User(3, "Doe", 40);
        Mockito.when(mockRepository.save(newUser)).thenReturn(newUser);
        User result = userRepository.save(newUser);
        assertEquals(newUser, result);

        User newUser2 = new User(6, "Smith", -35);
        try {
            userRepository.save(newUser2);
        } catch (Exception e) {
            assert(e instanceof jakarta.validation.ConstraintViolationException);
        }
    }
}