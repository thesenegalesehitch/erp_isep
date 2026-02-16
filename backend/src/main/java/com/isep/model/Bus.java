package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Bus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "line_number", nullable = false, unique = true)
    private String lineNumber;
    
    @Column(name = "driver_name")
    private String driverName;
    
    @Column(name = "driver_phone")
    private String driverPhone;
    
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats = 50;
    
    @Column(name = "current_location_lat")
    private Double currentLocationLat;
    
    @Column(name = "current_location_lng")
    private Double currentLocationLng;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusStatus status = BusStatus.STATIONARY;
    
    @Column(name = "departure_time")
    private LocalTime departureTime;
    
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    
    @Column(name = "route_description", columnDefinition = "TEXT")
    private String routeDescription;
    
    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusReservation> reservations = new HashSet<>();
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum BusStatus {
        STATIONARY,
        IN_TRANSIT,
        DELAYED,
        ARRIVED,
        CANCELLED
    }
    
    public Integer getAvailableSeats() {
        return totalSeats - reservations.size();
    }
}

