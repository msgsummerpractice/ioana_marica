package com.example.spring_project.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.example.spring_project.model.User;
import java.util.List;
import java.util.ArrayList;

@Repository
public class UserRepositoryImpl implements UserRepository<User> {
    private List<User> users = new ArrayList<>();

    public UserRepositoryImpl() {
        users.add(new User(1, "Alice123", "password1","alice@gmail.com", "Alice", "Smith"));
        users.add(new User(2, "Bob456", "password2", "bob@gmail.com", "Bob", "Johnson"));
    }

    @Override
    public List<User> findAll() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    @Override
    public User save(User user) {
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> update(User user) {
        return users.stream().filter(u -> u.getId() == user.getId())
        .findFirst()
        .map(foundUser -> {
            foundUser.setUsername(user.getUsername());
            foundUser.setPassword(user.getPassword());
            foundUser.setEmail(user.getEmail());
            foundUser.setFirstName(user.getFirstName());
            foundUser.setLastName(user.getLastName());
            return foundUser;
        });
    }

    @Override
    public boolean delete(User user){
        return users.removeIf(u -> u.getId() == user.getId());
    }

    @Override
    public Optional<User> getById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst();
    }

    @Override
    public List<User> getByName(String name) {  
        return users.stream().filter(u -> u.getUsername().equalsIgnoreCase(name)).toList();
    }

            

}
