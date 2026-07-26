package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import com.example.demo.model.User;

@Repository
public class UserRepository implements IRepository<User> {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
        users.add(new User(1, "Alice", 20));
        users.add(new User(2, "Bob", 25));
    }

    public List<User> findAll() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

}
