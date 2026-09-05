package com.example.fullstack.HomeFixApplication.Respository;

import com.example.fullstack.HomeFixApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This allows us to find a user by their email later for Login
    Optional<User> findByEmail(String email);
}