package com.example.spring_project.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.spring_project.dto.response.UserResponse;
import com.example.spring_project.model.User;

public interface UserService<T> {
    public Page<UserResponse> getAll(int page, int size, String sortBy);

    public UserResponse saveEntity(User user);

    public UserResponse updateEntity(int id, User user);

    public void deleteEntityByID(int id);

    public UserResponse getById(int id);

    public UserResponse getByEmail(String email);

    public UserResponse getByUsername(String username);

    public List<UserResponse> findTop10ByOrderByUsernameAsc(String username);

    public int countUsers();

    public UserResponse updateEmailById(int id, User user);
}
