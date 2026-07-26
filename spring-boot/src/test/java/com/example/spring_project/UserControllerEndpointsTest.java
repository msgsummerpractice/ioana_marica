package com.example.spring_project;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.spring_project.controller.UserController;
import com.example.spring_project.service.UserServiceImpl;
 
 
@WebMvcTest(UserController.class)
public class UserControllerEndpointsTest {
   @MockitoBean
   private UserServiceImpl userService;
   @Autowired
   private MockMvc mvc;
   @Test
   public void testEndpoint() throws Exception {
       Mockito.when(userService.getAll()).thenReturn(java.util.Collections.emptyList());
       mvc.perform(MockMvcRequestBuilders.get("/users/all"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
   }
}