package com.example.demo.repository;

import java.util.List;

public interface IRepository<T> {
    public List<T> findAll();

    public void save(T t);

}
