package com.example.spring_project.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}