package com.example.fullstack.HomeFixApplication.DTO;

import com.example.fullstack.HomeFixApplication.Entity.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
}