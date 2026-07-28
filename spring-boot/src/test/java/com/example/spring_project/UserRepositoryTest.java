package com.example.spring_project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.spring_project.model.User;
import com.example.spring_project.model.Roles;
import com.example.spring_project.model.Role;
import com.example.spring_project.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

        @Mock
        private UserRepository mockRepository;

        private Roles createRole(Long id, Role roleName) {

                Roles role = new Roles();
                role.setId(id);
                role.setName(roleName);

                return role;
        }

        private User createUser(Integer id,
                        String username,
                        String email,
                        String firstName,
                        String lastName) {

                User user = new User();

                user.setId(id);
                user.setUsername(username);
                user.setPassword("password123");
                user.setEmail(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setRoles(Set.of(createRole(1L, Role.USER)));
                return user;
        }

        @Test
        void testGetAllUsers() {

                List<User> mockUsers = Arrays.asList(

                                createUser(
                                                1,
                                                "John123",
                                                "john@example.com",
                                                "John",
                                                "Doe"),

                                createUser(
                                                2,
                                                "Jane",
                                                "jane@example.com",
                                                "Jane",
                                                "Smith"));

                Mockito.when(mockRepository.findAll())
                                .thenReturn(mockUsers);

                List<User> result = mockRepository.findAll();
                assertEquals(mockUsers, result);

        }

        @Test
        void testSaveUser() {

                User newUser = createUser(
                                3,
                                "Alice",
                                "alice@example.com",
                                "Alice",
                                "Doe");

                Mockito.when(mockRepository.save(newUser))
                                .thenReturn(newUser);

                User result = mockRepository.save(newUser);
                assertEquals(newUser, result);
                Mockito.verify(mockRepository)
                                .save(newUser);

        }

        @Test
        void testGetByEmail() {

                User mockUser = createUser(
                                1,
                                "John123",
                                "john@example.com",
                                "John",
                                "Doe");

                Mockito.when(mockRepository.getByEmail("john@example.com")).thenReturn(mockUser);

                User result = mockRepository.getByEmail("john@example.com");
                assertEquals(mockUser, result);

        }

        @Test
        void testDeleteUser() {

                mockRepository.deleteById(1);
                Mockito.verify(mockRepository).deleteById(1);

        }

        @Test
        void testUpdateUser() {

                User existingUser = createUser(
                                1,
                                "John123",
                                "john@example.com",
                                "John",
                                "Doe");

                existingUser.setUsername("JohnUpdated");

                Mockito.when(mockRepository.save(existingUser)).thenReturn(existingUser);

                User result = mockRepository.save(existingUser);

                assertEquals("JohnUpdated", result.getUsername());

                Mockito.verify(mockRepository).save(existingUser);

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

                                createUser(
                                                1,
                                                "Alice",
                                                "alice@example.com",
                                                "Alice",
                                                "Smith"),

                                createUser(
                                                2,
                                                "Bob",
                                                "bob@example.com",
                                                "Bob",
                                                "Johnson")

                );

                Mockito.when(mockRepository.findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc("Alice"))
                                .thenReturn(mockUsers);

                List<User> result = mockRepository.findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc("Alice");

                assertEquals(mockUsers, result);

        }

        @Test
        void testUserHasRole() {

                User user = createUser(
                                1,
                                "AdminUser",
                                "admin@example.com",
                                "Admin",
                                "User");

                assertEquals(
                                Role.USER,
                                user.getRoles()
                                .iterator()
                                .next()
                                .getName());

        }

}