package com.example.spring_project;

import com.example.spring_project.controller.UserController;
import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerEndpointsTest {

      @MockitoBean
      private UserServiceImpl userService;

      @Autowired
      private MockMvc mvc;

      @Test
      void testShowUsersEndpoint() throws Exception {

            when(userService.getAll()).thenReturn(Collections.emptyList());

            mvc.perform(get("/users"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

            verify(userService).getAll();
      }

      @Test
      void testAddUserEndpoint() throws Exception {

            UserResponse response = new UserResponse(
                        1,
                        "John123",
                        "john@example.com",
                        "John",
                        "Doe");

            when(userService.saveEntity(any(UserRequest.class)))
                        .thenReturn(response);

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
                        .andExpect(status().isCreated())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.username").value("John123"))
                        .andExpect(jsonPath("$.email").value("john@example.com"));

            verify(userService).saveEntity(any(UserRequest.class));
      }

      @Test
      void testDeleteUserEndpoint() throws Exception {
            doNothing().when(userService).deleteEntityByID(any(UserRequest.class));

            mvc.perform(delete("/users/1")
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
                        .andExpect(status().isNoContent());

            verify(userService).deleteEntityByID(any(UserRequest.class));

      }

      @Test
      void testUpdateUserEndpoint() throws Exception {

            UserResponse response = new UserResponse(
                        1,
                        "John123",
                        "john@example.com",
                        "John",
                        "Doe");

            when(userService.updateEntity(any(UserRequest.class)))
                        .thenReturn(response);

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
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.username").value("John123"))
                        .andExpect(jsonPath("$.email").value("john@example.com"));

            verify(userService).updateEntity(any(UserRequest.class));
      }

      @Test
      void testGetUserByIdEndpoint() throws Exception {
            when(userService.getById(any(UserRequest.class)))
                        .thenReturn(new UserResponse(
                                    1,
                                    "John123",
                                    "john@example.com",
                                    "John",
                                    "Doe"));

            mvc.perform(get("/users/1")
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
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.username").value("John123"))
                        .andExpect(jsonPath("$.email").value("john@example.com"));
      }

      @Test
      void testGetUserByEmailEndpoint() throws Exception {
            when(userService.getByEmail(any(UserRequest.class)))
                        .thenReturn(new UserResponse(
                                    1,
                                    "John123",
                                    "john@example.com",
                                    "John",
                                    "Doe"));

            mvc.perform(get("/users")
                        .param("email", "john@example.com"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                        .andExpect(jsonPath("$.email").value("john@example.com"));

            verify(userService).getByEmail(any(UserRequest.class));
      }

      @Test
      void testGetUserByUsernameEndpoint() throws Exception {
            when(userService.getByUsername(any(UserRequest.class)))
                        .thenReturn(new UserResponse(
                                    1,
                                    "John123",
                                    "john@example.com",
                                    "John",
                                    "Doe"));

            mvc.perform(get("/users")
                        .param("username", "John123"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                        .andExpect(jsonPath("$.username").value("John123"));

            verify(userService).getByUsername(any(UserRequest.class));
      }

      @Test
      void testGetTop10UsersEndpoint() throws Exception {

            when(userService.findTop10ByOrderByUsernameAsc())
                        .thenReturn(Collections.emptyList());

            mvc.perform(get("/users/top10"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

            verify(userService).findTop10ByOrderByUsernameAsc();
      }

      @Test
      void testCountUsersEndpoint() throws Exception {

            when(userService.countUsers()).thenReturn(5);

            mvc.perform(get("/users/count"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("5"));

            verify(userService).countUsers();
      }
}
