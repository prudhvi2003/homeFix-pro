package com.example.fullstack.HomeFixApplication.Service.Impl;

import com.example.fullstack.HomeFixApplication.DTO.BookingDTO;
import com.example.fullstack.HomeFixApplication.DTO.BookingRequest;
import com.example.fullstack.HomeFixApplication.Entity.*;
import com.example.fullstack.HomeFixApplication.Respository.*;
import com.example.fullstack.HomeFixApplication.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public BookingDTO createBooking(BookingRequest request, String userEmail) {
        log.info("Attempting to create booking | userEmail={} | serviceId={}", userEmail, request.getServiceId());

        try {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> {
                        log.warn("User not found | email={}", userEmail);
                        return new RuntimeException("User not found");
                    });

            HomeService homeService = serviceRepository.findById(request.getServiceId())
                    .orElseThrow(() -> {
                        log.warn("Service not found | serviceId={}", request.getServiceId());
                        return new RuntimeException("Service not found");
                    });

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setService(homeService);
            booking.setBookingDate(request.getBookingDate());
            booking.setStatus(BookingStatus.PENDING);

            Booking savedBooking = bookingRepository.save(booking);
            log.info("Booking created successfully | bookingId={} | userEmail={}", savedBooking.getId(), userEmail);

            BookingDTO dto = new BookingDTO();
            dto.setId(savedBooking.getId());
            dto.setServiceName(homeService.getName());
            dto.setUserName(user.getName());
            dto.setBookingDate(savedBooking.getBookingDate());
            dto.setStatus(savedBooking.getStatus());

            return dto;
        } catch (Exception e) {
            log.error("Booking creation failed | userEmail={} | serviceId={}", userEmail, request.getServiceId(), e);
            throw e;
        }
    }

    @Override
    public List<BookingDTO> getBookingsByUser(String email) {
        log.info("Fetching bookings for user | email={}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found | email={}", email);
                    return new RuntimeException("User not found");
                });

        List<BookingDTO> bookings = bookingRepository.findByUserId(user.getId()).stream()
                .map(booking -> {
                    BookingDTO dto = new BookingDTO();
                    dto.setId(booking.getId());
                    dto.setServiceName(booking.getService().getName());
                    dto.setUserName(user.getName());
                    dto.setBookingDate(booking.getBookingDate());
                    dto.setStatus(booking.getStatus());
                    return dto;
                }).collect(Collectors.toList());

        log.info("Retrieved {} bookings for user | email={}", bookings.size(), email);
        return bookings;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        log.info("Fetching all bookings");

        List<BookingDTO> bookings = bookingRepository.findAll().stream()
                .map(booking -> {
                    BookingDTO dto = new BookingDTO();
                    dto.setId(booking.getId());
                    dto.setServiceName(booking.getService().getName());
                    dto.setUserName(booking.getUser().getName());
                    dto.setBookingDate(booking.getBookingDate());
                    dto.setStatus(booking.getStatus());
                    return dto;
                }).collect(Collectors.toList());

        log.info("Retrieved {} total bookings", bookings.size());
        return bookings;
    }

    @Override
    public BookingDTO updateBookingStatus(Long bookingId, BookingStatus status) {
        log.info("Updating booking status | bookingId={} | newStatus={}", bookingId, status);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    log.warn("Booking not found | bookingId={}", bookingId);
                    return new RuntimeException("Booking not found");
                });

        booking.setStatus(status);
        Booking updated = bookingRepository.save(booking);

        log.info("Booking status updated successfully | bookingId={} | newStatus={}", updated.getId(), updated.getStatus());

        BookingDTO dto = new BookingDTO();
        dto.setId(updated.getId());
        dto.setServiceName(updated.getService().getName());
        dto.setUserName(updated.getUser().getName());
        dto.setBookingDate(updated.getBookingDate());
        dto.setStatus(updated.getStatus());

        return dto;
    }

    @Override
    public void cancelBooking(Long id, String email) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Security check: Ensure the person canceling is the owner of the booking
        if (!booking.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not authorized to cancel this booking");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Only pending bookings can be cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
