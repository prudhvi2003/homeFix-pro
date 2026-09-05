package com.example.fullstack.HomeFixApplication.Service;

import com.example.fullstack.HomeFixApplication.DTO.ServiceDTO;
import com.example.fullstack.HomeFixApplication.Entity.HomeService;
import com.example.fullstack.HomeFixApplication.Mapper.EntityMapper;
import com.example.fullstack.HomeFixApplication.Respository.ServiceRepository;
import com.example.fullstack.HomeFixApplication.Service.Impl.HomeServiceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomeServiceTest {

    @Mock
    private ServiceRepository serviceRepository; // Create a "Fake" repository

    @Mock
    private EntityMapper entityMapper; // Create a "Fake" mapper

    @InjectMocks
    private HomeServiceServiceImpl homeServiceService; // Inject the fakes into the real service

    @Test
    public void testGetAllServices() {
        // 1. ARRANGE (Set up our fake data)
        HomeService fakeService = new HomeService();
        fakeService.setName("AC Repair");

        ServiceDTO fakeDto = new ServiceDTO();
        fakeDto.setName("AC Repair");

        // Tell the "Fake" repository to return our list when called
        when(serviceRepository.findAll()).thenReturn(List.of(fakeService));
        // Tell the "Fake" mapper to return our DTO when called
        when(entityMapper.mapServiceToDTO(fakeService)).thenReturn(fakeDto);

        // 2. ACT (Run the actual method we want to test)
        List<ServiceDTO> result = homeServiceService.getAllServices(null, null);

        // 3. ASSERT (Verify the results are correct)
        assertEquals(1, result.size());
        assertEquals("AC Repair", result.get(0).getName());
    }
}