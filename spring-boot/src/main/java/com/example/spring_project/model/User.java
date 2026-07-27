package com.example.spring_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username", nullable = false, unique = true)
    @Size(min = 3, max = 20, message = "Username must be between 3 and 30 characters")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Column(name="password", nullable = false)
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 5 characters long")
    private String password;

    @Column(name="email", nullable = false, unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50, message = "Email must be less than 50 characters")
    private String email;

    @Column(name="firstname", nullable = false)
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 20, message = "First name must be less than 20 characters")
    private String firstName;

    @Column(name="lastname", nullable = false)  
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 20, message = "Last name must be less than 20 characters")
    private String lastName;

}
