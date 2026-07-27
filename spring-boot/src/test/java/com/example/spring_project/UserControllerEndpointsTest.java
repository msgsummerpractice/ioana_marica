package com.example.spring_project;

import com.example.spring_project.controller.UserController;
import com.example.spring_project.model.User;
import com.example.spring_project.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerEndpointsTest {

      @MockitoBean
      private UserServiceImpl userService;

      @Autowired
      private MockMvc mvc;

      @Test
      public void testShowUsersEndpoint() throws Exception {
            when(userService.getAll()).thenReturn(Collections.emptyList());
            mvc.perform(get("/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
            verify(userService).getAll();
      }

      @Test
      public void testAddUserEndpoint() throws Exception {
            User user = new User(1, "John123", "password123", "john@example.com", "John", "Doe");
            when(userService.saveEntity(any(User.class)))
                        .thenReturn(user);
            mvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                    {
                                        "id":1,
                                        "username":"John123",
                                        "password":"password123",
                                        "email":"john@example.com",
                                        "firstName":"John",
                                        "lastName":"Doe"
                                    }
                                    """))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
            verify(userService).saveEntity(any(User.class));
      }

      @Test
      public void testDeleteUserEndpoint() throws Exception {
            doNothing().when(userService).deleteEntityByID(eq(1));
            mvc.perform(delete("/users/1"))
                        .andExpect(status().isNoContent());
            verify(userService).deleteEntityByID(eq(1));
      }

      @Test
      public void testUpdateUserEndpoint() throws Exception {
            User user = new User(1, "John123", "password123", "john@example.com", "John", "Doe");
            when(userService.updateEntity(any(User.class)))
                        .thenReturn(user);
            mvc.perform(put("/users/1")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                    {
                                        "id":1,
                                        "username":"John123",
                                        "password":"password123",
                                        "email":"john@example.com",
                                        "firstName":"John",
                                        "lastName":"Doe"
                                    }
                                    """))
                        .andExpect(status().isOk());
            verify(userService).updateEntity(any(User.class));
      }

      @Test
      public void testGetUserByIdEndpoint() throws Exception {
            when(userService.getById(1))
                        .thenReturn(new User(1, "John123", "password123", "john@example.com", "John", "Doe"));
            mvc.perform(get("/users/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
            verify(userService).getById(1);
      }

      @Test
      public void testGetUsersByEmaiEndpoint() throws Exception {
            when(userService.getByEmail("john@example.com"))
                        .thenReturn(new User(1, "John123", "password123", "john@example.com", "John", "Doe"));
            mvc.perform(get("/users")
                        .param("email", "john@example.com"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

            verify(userService).getByEmail("john@example.com");
      }

      @Test
      public void testGetUsersByUsernameEndpoint() throws Exception {
            when(userService.getByUsername("John123"))
                        .thenReturn(new User(1, "John123", "password123", "john@example.com", "John", "Doe"));
            mvc.perform(get("/users")
                        .param("username", "John123"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

            verify(userService).getByUsername("John123");
      }
}