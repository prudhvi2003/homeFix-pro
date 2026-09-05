package com.example.fullstack.HomeFixApplication.Mapper;

import com.example.fullstack.HomeFixApplication.DTO.ServiceDTO;
import com.example.fullstack.HomeFixApplication.Entity.HomeService;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public ServiceDTO mapServiceToDTO(HomeService service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        dto.setPrice(service.getPrice());
        dto.setCategory(service.getCategory());
        dto.setAvailable(service.isAvailable());
        // ADD THIS LINE
        dto.setImageUrl(service.getImageUrl());
        return dto;
    }

    public HomeService mapDTOToService(ServiceDTO dto) {
        HomeService service = new HomeService();
        service.setId(dto.getId());
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        service.setCategory(dto.getCategory());
        service.setAvailable(dto.isAvailable());
        // ADD THIS LINE
        service.setImageUrl(dto.getImageUrl());
        return service;
    }
}