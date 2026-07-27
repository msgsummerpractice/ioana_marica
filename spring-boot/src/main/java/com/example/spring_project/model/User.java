package com.example.spring_project.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotNull(message = "Id cannot be null")
    private int id;
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank
    private String lastName;




}
