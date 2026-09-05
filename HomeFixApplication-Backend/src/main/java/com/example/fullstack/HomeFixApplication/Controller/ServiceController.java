package com.example.fullstack.HomeFixApplication.Controller;

import com.example.fullstack.HomeFixApplication.DTO.ServiceDTO;
import com.example.fullstack.HomeFixApplication.Service.HomeServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@Tag(name = "Home Services", description = "Endpoints for managing the service catalog")
public class ServiceController {

    @Autowired
    private HomeServiceService homeServiceService; // Injecting the Service, not Repository!

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ServiceDTO createService(
            @RequestPart("service") @Valid ServiceDTO serviceDTO, // Key must be "service"
            @RequestPart("image") MultipartFile image) {          // Key must be "image"
        return homeServiceService.createService(serviceDTO, image);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        homeServiceService.deleteService(id);
    }

    @GetMapping
    @Operation(summary = "Get all services", description = "Fetch a list of all available home services with optional filters")
    public List<ServiceDTO> getAllServices(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {
        return homeServiceService.getAllServices(search, category);
    }
}