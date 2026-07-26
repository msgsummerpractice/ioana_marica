package com.example.spring_project.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.example.spring_project.model.User;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;

@Repository
public class UserRepositoryImpl implements UserRepository<User> {
    private List<User> users = new ArrayList<>();

    public UserRepositoryImpl() {
        users.add(new User(1, "Alice", 20));
        users.add(new User(2, "Bob", 25));
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
            foundUser.setName(user.getName());
            foundUser.setAge(user.getAge());
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
        return users.stream().filter(u -> u.getName().equalsIgnoreCase(name)).toList();
    }

            

}
