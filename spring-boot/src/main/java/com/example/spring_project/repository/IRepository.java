package com.example.spring_project.repository;

import java.util.List;

public interface IRepository<T> {
    public List<T> findAll();

    public T save(T t);

}
