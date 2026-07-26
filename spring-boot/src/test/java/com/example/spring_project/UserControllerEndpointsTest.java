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
      User user = new User(1, "John Doe", 30);
      when(userService.saveEntity(any(User.class)))
            .thenReturn(user);
      mvc.perform(post("/users")
            .contentType(APPLICATION_JSON)
            .content("""
                  {
                      "id":1,
                      "name":"John Doe",
                      "age":30
                  }
                  """))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
      verify(userService).saveEntity(any(User.class));
   }

   @Test
   public void testDeleteUserEndpoint() throws Exception {
      doNothing().when(userService).deleteEntity(any());
      mvc.perform(delete("/users/1"))
            .andExpect(status().isNoContent());
      verify(userService).deleteEntity(any());
   }

   @Test
   public void testUpdateUserEndpoint() throws Exception {
      User user = new User(1, "John Doe", 30);
      when(userService.updateEntity(any(User.class)))
            .thenReturn(user);
      mvc.perform(put("/users/1")
            .contentType(APPLICATION_JSON)
            .content("""
                  {
                      "id":1,
                      "name":"John Doe",
                      "age":30
                  }
                  """))
            .andExpect(status().isOk());
      verify(userService).updateEntity(any(User.class));
   }

   @Test
   public void testGetUserByIdEndpoint() throws Exception {
      when(userService.getById(1))
            .thenReturn(new User(1, "John Doe", 30));
      mvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
      verify(userService).getById(1);
   }

   @Test
   public void testGetUsersByNameEndpoint() throws Exception {
      when(userService.getByName("John"))
            .thenReturn(Collections.singletonList(
                  new User(1, "John Doe", 30)));
      mvc.perform(get("/users/name/John"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
      verify(userService).getByName("John");
   }
}
