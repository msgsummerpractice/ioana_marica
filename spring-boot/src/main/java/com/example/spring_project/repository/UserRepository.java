package com.example.spring_project.repository;

import com.example.spring_project.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);

    User getByUsername(String username);

    boolean existsByEmail(String email);

    List<User> findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc(String searchParam);

    @Query(value = "SELECT COUNT(*) FROM User", nativeQuery = true)
    int countUsers();

}
