package com.example.fullstack.HomeFixApplication.Service.Impl;

import com.example.fullstack.HomeFixApplication.DTO.AuthResponse;
import com.example.fullstack.HomeFixApplication.DTO.LoginRequest;
import com.example.fullstack.HomeFixApplication.DTO.UserDTO;
import com.example.fullstack.HomeFixApplication.Entity.User;
import com.example.fullstack.HomeFixApplication.Entity.UserRole;
import com.example.fullstack.HomeFixApplication.Mapper.EntityMapper;
import com.example.fullstack.HomeFixApplication.Respository.UserRepository;
import com.example.fullstack.HomeFixApplication.Security.JwtUtils;
import com.example.fullstack.HomeFixApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDTO registerUser(User user) {
        // 1. Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 2. Set default role if none provided
        if (user.getRole() == null) {
            user.setRole(UserRole.CUSTOMER);
        }

        // 3. Save to DB
        User savedUser = userRepository.save(user);

        // 4. Return as DTO (Security!)
        UserDTO dto = new UserDTO();
        dto.setId(savedUser.getId());
        dto.setName(savedUser.getName());
        dto.setEmail(savedUser.getEmail());
        dto.setRole(savedUser.getRole());
        return dto;
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        // 1. Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Generate Token
        String token = jwtUtils.generateToken(user.getEmail());

        // 4. Return Token + User Info (excluding password)
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        return new AuthResponse(token, userDTO);
    }
}