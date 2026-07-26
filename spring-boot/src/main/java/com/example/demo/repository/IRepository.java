package com.example.demo.repository;

import java.util.List;

public interface IRepository<T> {
    public List<T> findAll();

    public T save(T t);

}
