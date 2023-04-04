package com.example.demoproject.repository;

import com.example.demoproject.model.User1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User1,Long> {
}
