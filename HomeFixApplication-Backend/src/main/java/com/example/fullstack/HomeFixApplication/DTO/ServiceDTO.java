package com.example.fullstack.HomeFixApplication.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ServiceDTO {
    private Long id;

    @NotBlank(message = "Service name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    private boolean available;
    private String imageUrl;
}