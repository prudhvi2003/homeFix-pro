package com.example.fullstack.HomeFixApplication.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.fullstack.HomeFixApplication.DTO.ServiceDTO;
import com.example.fullstack.HomeFixApplication.Entity.HomeService;
import com.example.fullstack.HomeFixApplication.Mapper.EntityMapper;
import com.example.fullstack.HomeFixApplication.Respository.ServiceRepository;
import com.example.fullstack.HomeFixApplication.Service.HomeServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeServiceServiceImpl implements HomeServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO, MultipartFile imageFile) {
        HomeService homeService = entityMapper.mapDTOToService(serviceDTO);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 2. Use the pre-configured cloudinary object
                Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
                String publicUrl = uploadResult.get("secure_url").toString();
                homeService.setImageUrl(publicUrl);
            } catch (IOException e) {
                throw new RuntimeException("Cloudinary upload failed: " + e.getMessage());
            }
        }

        HomeService savedService = serviceRepository.save(homeService);
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
    public List<ServiceDTO> getAllServices(String search, String category) {
        List<HomeService> services;

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