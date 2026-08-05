package com.example.spring_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private String token;
    private Set<String> roles;
    private boolean mfaRequired;
    private String message;
}