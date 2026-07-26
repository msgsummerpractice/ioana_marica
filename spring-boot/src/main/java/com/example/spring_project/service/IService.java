package com.example.spring_project.service;

import java.util.List;

public interface IService<T> {
    public List<T> getAll();
    public int count();
    public T saveEntity(T entity);

}
