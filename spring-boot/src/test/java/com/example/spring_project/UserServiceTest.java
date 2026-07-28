package com.example.spring_project;

import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.mapper.UserMapper;
import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepository;
import com.example.spring_project.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

        @Mock
        private UserRepository userRepository;

        private UserMapper userMapper;
        private UserServiceImpl userService;

        @BeforeEach
        void setUp() {
                userMapper = new UserMapper();
                userService = new UserServiceImpl(userRepository, userMapper);
        }

        @Test
        void testGetAllUsers() {
                List<User> mockUsers = Arrays.asList(
                                new User(1, "John123", "password123", "john@example.com", "John", "Doe"),
                                new User(2, "Jane", "password456", "jane@example.com", "Jane", "Smith")
                );
                Mockito.when(userRepository.findAll()).thenReturn(mockUsers);
                List<User> result = userRepository.findAll();
                assertEquals(mockUsers, result);
        }

        @Test
        void testSaveUser() {

                User user = new User(
                                1,
                                "John123",
                                "password123",
                                "john@example.com",
                                "John",
                                "Doe");

                Mockito.when(userRepository.save(Mockito.any(User.class)))
                                .thenReturn(user);

                UserResponse response = userService.saveEntity(user);

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

                Mockito.when(userRepository.findById(1))
                                .thenReturn(Optional.of(user));

                Mockito.when(userRepository.save(Mockito.any(User.class)))
                                .thenReturn(user);

                UserResponse response = userService.updateEntity(1, user);

                assertEquals("John123", response.getUsername());

                Mockito.verify(userRepository).findById(1);
                Mockito.verify(userRepository).save(Mockito.any(User.class));
        }

        @Test
        void testDeleteUser() {

                Mockito.when(userRepository.existsById(1)).thenReturn(true);

                userService.deleteEntityByID(1);

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
                                .thenReturn(Optional.of(user));

                UserResponse response = userService.getById(1);

                assertNotNull(response);
                assertEquals("John123", response.getUsername());

                Mockito.verify(userRepository).findById(1);
        }

        @Test
        void testFindTop10ByOrderByUsernameAsc() {

                List<User> mockUsers = Arrays.asList(
                                new User(1, "Alice", "password1", "alice@example.com", "Alice", "Smith"),
                                new User(2, "Bob", "password2", "bob@example.com", "Bob", "Johnson"));

                Mockito.when(userRepository.findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc("Alice"))
                                .thenReturn(mockUsers);

                List<UserResponse> result = userService.findTop10ByOrderByUsernameAsc("Alice");

                assertEquals(2, result.size());
                assertEquals("Alice", result.get(0).getUsername());
                assertEquals("Bob", result.get(1).getUsername());

                Mockito.verify(userRepository)
                                .findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc("Alice");
        }

        @Test
        void testCountUsers() {

                Mockito.when(userRepository.countUsers()).thenReturn(5);

                int result = userService.countUsers();

                assertEquals(5, result);

                Mockito.verify(userRepository).countUsers();
        }
}
