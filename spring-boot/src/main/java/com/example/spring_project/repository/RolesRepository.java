package com.example.spring_project.repository;

import com.example.spring_project.model.Role;
import com.example.spring_project.model.Roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(Role name);
}