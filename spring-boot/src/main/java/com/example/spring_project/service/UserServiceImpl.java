package com.example.spring_project.service;

import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<User> {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
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
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User with ID " + user.getId() + " does not exist."));

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteEntityByID(int id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " does not exist."));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public List<User> findTop10ByOrderByUsernameAsc() {
        return userRepository.findTop10ByOrderByUsernameAsc();
    }

    @Override
    public int countUsers() {
        return userRepository.countUsers();
    }

}
