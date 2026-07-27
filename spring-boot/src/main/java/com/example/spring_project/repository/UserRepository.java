package com.example.spring_project.repository;

import com.example.spring_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);
    User getByUsername(String username);
}
