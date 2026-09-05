package com.example.fullstack.HomeFixApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELATIONSHIP: Many bookings can belong to ONE User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // RELATIONSHIP: Many bookings can be for ONE Service
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private HomeService service;

    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDateTime createdAt;

    // This method runs automatically before the record is saved to the DB
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = BookingStatus.PENDING;
        }
    }
}