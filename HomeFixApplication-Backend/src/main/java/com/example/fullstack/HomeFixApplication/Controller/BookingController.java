package com.example.fullstack.HomeFixApplication.Controller;

import com.example.fullstack.HomeFixApplication.DTO.BookingDTO;
import com.example.fullstack.HomeFixApplication.DTO.BookingRequest;
import com.example.fullstack.HomeFixApplication.Entity.BookingStatus;
import com.example.fullstack.HomeFixApplication.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public BookingDTO book(@RequestBody BookingRequest request, @RequestParam String email) {
        // NOTE: For now, we pass email as a param.
        // Later, we will get it automatically from the JWT for better security.
        return bookingService.createBooking(request, email);
    }

    @GetMapping("/my-bookings")
    public List<BookingDTO> getMyBookings(@RequestParam String email) {
        return bookingService.getBookingsByUser(email);
    }

    @GetMapping("/all")
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping("/{id}/status")
    public BookingDTO updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return bookingService.updateBookingStatus(id, status);
    }

    @DeleteMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id, @RequestParam String email) {
        bookingService.cancelBooking(id, email);
    }
}