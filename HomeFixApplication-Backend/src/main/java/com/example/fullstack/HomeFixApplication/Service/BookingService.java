package com.example.fullstack.HomeFixApplication.Service;

import com.example.fullstack.HomeFixApplication.DTO.BookingDTO;
import com.example.fullstack.HomeFixApplication.DTO.BookingRequest;
import com.example.fullstack.HomeFixApplication.Entity.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingRequest request, String userEmail);

    List<BookingDTO> getBookingsByUser(String email);

    List<BookingDTO> getAllBookings();
    BookingDTO updateBookingStatus(Long bookingId, BookingStatus status);

    void cancelBooking(Long id, String email);
}