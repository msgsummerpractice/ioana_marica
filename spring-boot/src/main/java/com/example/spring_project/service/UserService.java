package com.example.spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements IService<User> {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public int count() {
        List<User> users = userRepository.findAll();
        return users.size();
    }
    @Override
    public User saveEntity(User user) {
        return userRepository.save(user);
    }

}
