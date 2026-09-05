package com.example.fullstack.HomeFixApplication.Respository;

import com.example.fullstack.HomeFixApplication.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // This custom method allows us to find all bookings for a specific user
    List<Booking> findByUserId(Long userId);
}