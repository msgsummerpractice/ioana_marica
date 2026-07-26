package com.example.spring_project.service;

import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepositoryImpl;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<User> {

    private final UserRepositoryImpl userRepository;

    public UserServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveEntity(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateEntity(User user) {
        return userRepository.update(user)
        .orElseThrow(() -> new NoSuchElementException("User with ID " + user.getId() + " does not exist."));
    }

    @Override
    public void deleteEntity(User user) {
        boolean removed = userRepository.delete(user);
        if (!removed) {
            throw new NoSuchElementException("User with ID " + user.getId() + " does not exist.");
        }
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id)
        .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " does not exist."));
    }

    @Override
    public List<User> getByName(String name) {
        return userRepository.getByName(name);
    }

}
