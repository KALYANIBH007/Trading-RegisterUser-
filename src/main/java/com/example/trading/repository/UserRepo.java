package com.example.trading.repository;

import com.example.trading.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByPanNumber(String panNumber);
}
