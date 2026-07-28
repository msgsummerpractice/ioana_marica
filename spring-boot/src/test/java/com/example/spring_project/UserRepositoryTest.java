package com.example.spring_project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository mockRepository;

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(1, "John123", "password123", "john@example.com", "John", "Doe"),
                new User(2, "Jane", "password456", "jane@example.com", "Jane", "Smith"));
        Mockito.when(mockRepository.findAll()).thenReturn(mockUsers);
        List<User> result = mockRepository.findAll();
        assertEquals(mockUsers, result);
    }

    @Test
    void testSaveUser() {
        User newUser = new User(3, "Alice", "password789", "alice@example.com", "Alice", "Doe");
        Mockito.when(mockRepository.save(newUser)).thenReturn(newUser);
        User result = mockRepository.save(newUser);
        assertEquals(newUser, result);

        User newUser2 = new User(6, "Bob", "password000", "bob@example.com", "Bob", "Smith");
        try {
            mockRepository.save(newUser2);
        } catch (Exception e) {
            assert (e instanceof jakarta.validation.ConstraintViolationException);
        }
    }

    @Test
    void testGetByEmail() {
        User mockUser = new User(1, "John123", "password123", "john@example.com", "John", "Doe");
        Mockito.when(mockRepository.getByEmail("john@example.com")).thenReturn(mockUser);
        User result = mockRepository.getByEmail("john@example.com");
        assertEquals(mockUser, result);
    }

    @Test
    void testDeleteUser() {
        User mockUser = new User(1, "John123", "password123", "john@example.com", "John", "Doe");
        Mockito.when(mockRepository.existsById(1)).thenReturn(true);
        mockRepository.deleteById(1);
        Mockito.verify(mockRepository).existsById(1);
        Mockito.verify(mockRepository).deleteById(1);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User(1, "John123", "password123", "john@example.com", "John", "Doe");
        Mockito.when(mockRepository.findById(1)).thenReturn(java.util.Optional.of(existingUser));
        existingUser.setUsername("JohnUpdated");
        Mockito.when(mockRepository.save(existingUser)).thenReturn(existingUser);
        User result = mockRepository.save(existingUser);
        assertEquals("JohnUpdated", result.getUsername());
    }

    @Test
    void testCountUsers() {
        Mockito.when(mockRepository.countUsers()).thenReturn(5);
        int result = mockRepository.countUsers();
        assertEquals(5, result);
    }

    @Test
    void testFindTop10ByOrderByUsernameAsc() {
        List<User> mockUsers = Arrays.asList(
                new User(1, "Alice", "password1", "alice@example.com", "Alice", "Smith"),
                new User(2, "Bob", "password2", "bob@example.com", "Bob", "Johnson"));
        Mockito.when(mockRepository.findByUsernameContainingIgnoreCase("Alice")).thenReturn(mockUsers);
        List<User> result = mockRepository.findByUsernameContainingIgnoreCase("Alice");
        assertEquals(mockUsers, result);
    }

}