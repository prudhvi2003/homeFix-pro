package com.example.fullstack.HomeFixApplication.Controller;

import com.example.fullstack.HomeFixApplication.DTO.AuthResponse;
import com.example.fullstack.HomeFixApplication.DTO.LoginRequest;
import com.example.fullstack.HomeFixApplication.DTO.UserDTO;
import com.example.fullstack.HomeFixApplication.Entity.User;
import com.example.fullstack.HomeFixApplication.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest)
    {
        log.info("Login attempt for user: {}", loginRequest.getEmail());
        return userService.loginUser(loginRequest);
    }
}