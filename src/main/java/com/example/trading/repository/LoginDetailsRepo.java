package com.example.trading.repository;

import com.example.trading.entity.LoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginDetailsRepo extends JpaRepository<LoginDetails, String> {
}
