package com.example.spring_project.dto.request;

import lombok.Data;

@Data
public class MfaVerifyRequest {
    private String username;
    private String token;
}
