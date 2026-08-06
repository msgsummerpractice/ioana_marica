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
        saveIfMissing(Role.USER);
        saveIfMissing(Role.ADMIN);
    }

    private void saveIfMissing(Role roleName) {
        if (rolesRepository.findByName(roleName).isEmpty()) {
            Roles role = new Roles();
            role.setName(roleName);
            rolesRepository.save(role);
        }
    }
}