package com.example.spring_project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MfaVerifyRequest {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Token cannot be blank")
    private String token;
}