package com.example.spring_project.mapper;

import org.springframework.stereotype.Component;

import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.model.User;

@Component
public class UserMapper {


    public UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }


    public User toEntity(UserRequest request) {

        User user = new User();

        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return user;
    }

}
