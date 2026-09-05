package com.example.fullstack.HomeFixApplication.Service;

import com.example.fullstack.HomeFixApplication.DTO.AuthResponse;
import com.example.fullstack.HomeFixApplication.DTO.LoginRequest;
import com.example.fullstack.HomeFixApplication.DTO.UserDTO;
import com.example.fullstack.HomeFixApplication.Entity.User;

public interface UserService {
    UserDTO registerUser(User user);

    AuthResponse loginUser(LoginRequest loginRequest);
}