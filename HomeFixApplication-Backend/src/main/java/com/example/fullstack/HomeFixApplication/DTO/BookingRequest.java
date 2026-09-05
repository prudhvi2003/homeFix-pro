package com.example.fullstack.HomeFixApplication.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private Long serviceId;
    private LocalDateTime bookingDate;
}