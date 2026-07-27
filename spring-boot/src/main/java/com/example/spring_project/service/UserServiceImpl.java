package com.example.spring_project.service;

import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import com.example.spring_project.dto.request.UpdateUserRequest;
import com.example.spring_project.dto.request.DeleteUserRequest;
import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.mapper.UserMapper;
import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.model.User;
import com.example.spring_project.repository.UserRepository;

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
    public UserResponse saveEntity(UserRequest request) {

        User user = new User();

        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateEntity(UserRequest request) {

        User existingUser = userRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        existingUser.setUsername(request.getUsername());
        existingUser.setPassword(request.getPassword());
        existingUser.setEmail(request.getEmail());
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteEntityByID(DeleteUserRequest request) {

        if (!userRepository.existsById(request.getId())) {
            throw new NoSuchElementException("User not found");
        }

        userRepository.deleteById(request.getId());
    }

    @Override
    public UserResponse getById(UserRequest request ) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getByEmail(UserRequest request) {

        User user = userRepository.getByEmail(request.getEmail());

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getByUsername(UserRequest request) {

        User user = userRepository.getByUsername(request.getUsername());

        if (user == null) {
            throw new NoSuchElementException("User not found");
        }

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> findTop10ByOrderByUsernameAsc() {

        return userRepository.findTop10ByOrderByUsernameAsc()
                .stream()
                .map(userMapper::toResponse)
                .limit(10)
                .toList();

    }

    @Override
    public int countUsers() {

        return userRepository.countUsers();
    }

    @Override
    public UserResponse updateEmailById(UpdateUserRequest request) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setEmail(request.getEmail());

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }
}