package com.example.spring_project;

import com.example.spring_project.controller.UserController;
import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.mapper.UserMapper;
import com.example.spring_project.model.User;
import com.example.spring_project.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.core.userdetails.UserDetailsService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerEndpointsTest {

      @MockitoBean
      private JwtTokenProvider jwtTokenProvider;

      @MockitoBean
      private JwtAuthenticationFilter jwtAuthenticationFilter;

      @MockitoBean
      private UserDetailsService userDetailsService;

      @MockitoBean
      private UserServiceImpl userService;

      @MockitoBean
      private UserMapper userMapper;

      @Autowired
      private MockMvc mvc;

      @Test
      void testShowUsersEndpoint() throws Exception {

            when(userService.getAll(0, 10, "id"))
                        .thenReturn(Page.empty());

            mvc.perform(get("/users"))
                        .andExpect(status().isOk());

            verify(userService)
                        .getAll(0, 10, "id");
      }

      @Test
      void testAddUserEndpoint() throws Exception {

            User user = new User();

            user.setId(1);
            user.setUsername("John123");
            user.setPassword("password123");
            user.setEmail("john@example.com");
            user.setFirstName("John");
            user.setLastName("Doe");

            UserResponse response = new UserResponse(
                        1,
                        "John123",
                        "john@example.com",
                        "John",
                        "Doe");

            when(userMapper.toEntity(any(UserRequest.class)))
                        .thenReturn(user);

            when(userService.saveEntity(any(User.class)))
                        .thenReturn(response);

            mvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                    {
                                        "username":"John123",
                                        "password":"password123",
                                        "email":"john@example.com",
                                        "firstName":"John",
                                        "lastName":"Doe"
                                    }
                                    """))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.username")
                                    .value("John123"));

      }

      @Test
      void testDeleteUserEndpoint() throws Exception {

            doNothing()
                        .when(userService)
                        .deleteEntityByID(1);

            mvc.perform(delete("/users/1"))
                        .andExpect(status().isNoContent());

            verify(userService)
                        .deleteEntityByID(1);

      }

      @Test
      void testUpdateUserEndpoint() throws Exception {

            User user = new User();

            user.setId(1);
            user.setUsername("John123");

            UserResponse response = new UserResponse(
                        1,
                        "John123",
                        "john@example.com",
                        "John",
                        "Doe");

            when(userMapper.toEntity(any(UserRequest.class)))
                        .thenReturn(user);

            when(userService.updateEntity(anyInt(), any(User.class)))
                        .thenReturn(response);

            mvc.perform(put("/users/1")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                    {
                                        "username":"John123",
                                        "password":"password123",
                                        "email":"john@example.com",
                                        "firstName":"John",
                                        "lastName":"Doe"
                                    }
                                    """))
                        .andExpect(status().isOk());

      }

      @Test
      void testGetUserByIdEndpoint() throws Exception {

            when(userService.getById(1))
                        .thenReturn(
                                    new UserResponse(
                                                1,
                                                "John123",
                                                "john@example.com",
                                                "John",
                                                "Doe"));

            mvc.perform(get("/users/1"))
                        .andExpect(status().isOk());

      }

      @Test
      void testCountUsersEndpoint() throws Exception {

            when(userService.countUsers())
                        .thenReturn(5);

            mvc.perform(get("/users/count"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("5"));

      }

}