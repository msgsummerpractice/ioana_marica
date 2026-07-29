package com.example.spring_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.exception_handling.DuplicateEmailException;
import com.example.spring_project.mapper.UserMapper;
import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<User> {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserResponse> getAll(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    public UserResponse saveEntity(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        User updatedUser = new User();

        updatedUser.setId(user.getId());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        updatedUser.setEmail(user.getEmail());

        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());

        User savedUser = userRepository.save(updatedUser);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateEntity(int id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
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
        return userRepository.findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc(username)
                .stream()
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