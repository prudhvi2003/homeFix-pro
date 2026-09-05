package com.example.fullstack.HomeFixApplication.DTO;

import com.example.fullstack.HomeFixApplication.Entity.BookingStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long serviceId;
    private String serviceName;
    private LocalDateTime bookingDate;
    private BookingStatus status;
}