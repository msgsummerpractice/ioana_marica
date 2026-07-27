package com.example.spring_project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class UpdateUserRequest {

        @Email(message = "Email must be valid")
        @NotNull(message = "Email cannot be null")
        private String email;

        private Integer id;

    }


