package com.example.spring_project.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteUserRequest {

    @NotNull
    private Integer id;
}
