package com.example.spring_project.service;

import com.example.spring_project.dto.request.SignInRequest;
import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.dto.response.SignInResponse;
import com.example.spring_project.dto.response.UserResponse;

public interface AuthService {
    SignInResponse login(SignInRequest request);

    UserResponse register(UserRequest request);
}