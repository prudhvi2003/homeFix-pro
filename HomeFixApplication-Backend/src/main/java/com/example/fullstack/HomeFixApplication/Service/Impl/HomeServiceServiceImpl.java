package com.example.fullstack.HomeFixApplication.Service.Impl;

import com.example.fullstack.HomeFixApplication.DTO.ServiceDTO;
import com.example.fullstack.HomeFixApplication.Entity.HomeService;
import com.example.fullstack.HomeFixApplication.Mapper.EntityMapper;
import com.example.fullstack.HomeFixApplication.Respository.ServiceRepository;
import com.example.fullstack.HomeFixApplication.Service.HomeServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeServiceServiceImpl implements HomeServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        // 1. Convert DTO to Entity
        HomeService homeService = entityMapper.mapDTOToService(serviceDTO);

        // 2. Save to DB
        HomeService savedService = serviceRepository.save(homeService);

        // 3. Return as DTO
        return entityMapper.mapServiceToDTO(savedService);
    }

    @Override
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new RuntimeException("Service not found");
        }
        serviceRepository.deleteById(id);
    }

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO, MultipartFile imageFile) {
        HomeService homeService = entityMapper.mapDTOToService(serviceDTO);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = "uploads/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // FIX: We name the file "photo_" instead of "images_" to bypass ad-blockers
                String originalName = imageFile.getOriginalFilename();
                String extension = originalName.substring(originalName.lastIndexOf("."));
                String fileName = "photo_" + System.currentTimeMillis() + extension;

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                homeService.setImageUrl("/uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Could not save image file", e);
            }
        }

        HomeService savedService = serviceRepository.save(homeService);
        return entityMapper.mapServiceToDTO(savedService);
    }

    @Override
    public List<ServiceDTO> getAllServices(String search, String category) {
        List<HomeService> services;

        // Check if search and category are actually provided (not null and not empty)
        boolean hasSearch = (search != null && !search.trim().isEmpty());
        boolean hasCategory = (category != null && !category.trim().isEmpty());

        if (hasSearch && hasCategory) {
            services = serviceRepository.findByNameContainingIgnoreCaseAndCategory(search, category);
        } else if (hasSearch) {
            services = serviceRepository.findByNameContainingIgnoreCase(search);
        } else if (hasCategory) {
            services = serviceRepository.findByCategory(category);
        } else {
            services = serviceRepository.findAll();
        }

        return services.stream()
                .map(entityMapper::mapServiceToDTO)
                .collect(Collectors.toList());
    }
}