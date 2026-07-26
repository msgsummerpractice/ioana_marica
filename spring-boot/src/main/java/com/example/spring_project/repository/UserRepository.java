package com.example.spring_project.repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository<T> {
    public List<T> findAll();

    public T save(T t);
    public Optional<T> update(T t);
    public boolean delete(T t);
    public Optional<T> getById(int id);
    public List<T> getByName(String name);
 
}
