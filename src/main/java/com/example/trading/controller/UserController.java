package com.example.trading.controller;

import com.example.trading.dto.UserDto;
import com.example.trading.service.UserProducer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserProducer userProducer;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody UserDto userDto){

        userProducer.registerUser(userDto);
        return ResponseEntity.ok(Map.of("statusCode ", "200", "statusMessage ", "User Registered Successfully "));
    }
}
