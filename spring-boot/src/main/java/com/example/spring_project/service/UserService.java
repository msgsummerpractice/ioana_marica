package com.example.spring_project.service;

import java.util.List;

import com.example.spring_project.dto.request.UpdateUserRequest;
import com.example.spring_project.dto.request.UserRequest;
import com.example.spring_project.dto.response.UserResponse;

    public interface UserService<T> {
    public List<UserResponse> getAll();

    public UserResponse saveEntity(UserRequest request);

    public UserResponse updateEntity(UserRequest request);

    public void deleteEntityByID(UserRequest request);

    public UserResponse getById(UserRequest request);

    public UserResponse getByEmail(UserRequest request);

    public UserResponse getByUsername(UserRequest request);

    public List<UserResponse> findTop10ByOrderByUsernameAsc();

    public int countUsers();

    public UserResponse updateEmailById(UpdateUserRequest request);
}
