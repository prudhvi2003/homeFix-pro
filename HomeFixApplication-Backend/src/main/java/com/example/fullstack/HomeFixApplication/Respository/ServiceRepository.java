package com.example.fullstack.HomeFixApplication.Respository;

import com.example.fullstack.HomeFixApplication.Entity.HomeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<HomeService, Long> {

    // 1. Search by name (Case Insensitive)
    List<HomeService> findByNameContainingIgnoreCase(String name);

    // 2. Filter by category
    List<HomeService> findByCategory(String category);

    // 3. Search by name AND category
    List<HomeService> findByNameContainingIgnoreCaseAndCategory(String name, String category);
}