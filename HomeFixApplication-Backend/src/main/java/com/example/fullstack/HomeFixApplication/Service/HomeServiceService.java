package com.example.fullstack.HomeFixApplication.Service;

import com.example.fullstack.HomeFixApplication.DTO.ServiceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HomeServiceService {
    ServiceDTO createService(ServiceDTO serviceDTO);

    void deleteService(Long id);
    ServiceDTO createService(ServiceDTO serviceDTO, MultipartFile imageFile);
    List<ServiceDTO> getAllServices(String search, String category);
}