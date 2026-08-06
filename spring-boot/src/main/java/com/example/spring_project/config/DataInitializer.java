package com.example.spring_project.config;

import com.example.spring_project.model.Role;
import com.example.spring_project.model.Roles;
import com.example.spring_project.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolesRepository rolesRepository;

    @Override
    public void run(String... args) {
        if (rolesRepository.findByName(Role.USER).isEmpty()) {
            Roles userRole = new Roles();
            userRole.setName(Role.USER);
            rolesRepository.save(userRole);
        }

        if (rolesRepository.findByName(Role.ADMIN).isEmpty()) {
            Roles adminRole = new Roles();
            adminRole.setName(Role.ADMIN);
            rolesRepository.save(adminRole);
        }
    }
}