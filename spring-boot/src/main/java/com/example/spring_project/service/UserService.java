package com.example.spring_project.service;

import java.util.List;

public interface UserService<T> {
    public List<T> getAll();
    public T saveEntity(T entity);
    public T updateEntity(T entity);
    public void deleteEntity(T entity);
    public T getById(int id);
    public List<T> getByName(String name);

}
