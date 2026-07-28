package com.example.spring_project.service;

import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.example.spring_project.dto.request.UpdateUserRequest;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.mapper.UserMapper;
import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService<User> {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponse> getAll() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse saveEntity(User user) {

        User user_updated = new User();

        user_updated.setId(user.getId());
        user_updated.setUsername(user.getUsername());
        user_updated.setPassword(user.getPassword());
        user_updated.setEmail(user.getEmail());
        user_updated.setFirstName(user.getFirstName());
        user_updated.setLastName(user.getLastName());

        User savedUser = userRepository.save(user_updated);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateEntity(int id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteEntityByID(int id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getById(int Id) {
        User user = userRepository.findById(Id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getByEmail(String email) {

        User user = userRepository.getByEmail(email);

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getByUsername(String username) {

        User user = userRepository.getByUsername(username);

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> findTop10ByOrderByUsernameAsc(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username)
                .stream()
                .sorted(Comparator.comparing(User::getUsername, String.CASE_INSENSITIVE_ORDER))
                .limit(10)
                .map(userMapper::toResponse)
                .collect(Collectors.toList());

    }

    @Override
    public int countUsers() {

        return userRepository.countUsers();
    }

    @Override
    public UserResponse updateEmailById(int id, User user) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        existingUser.setEmail(user.getEmail());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponse(updatedUser);
    }
}