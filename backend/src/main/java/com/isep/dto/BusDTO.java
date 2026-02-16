package com.isep.dto;

import com.isep.model.Bus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private Long id;
    private String lineNumber;
    private String driverName;
    private String driverPhone;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double currentLocationLat;
    private Double currentLocationLng;
    private Bus.BusStatus status;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String routeDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BusDTO fromEntity(Bus bus) {
        BusDTO dto = new BusDTO();
        dto.setId(bus.getId());
        dto.setLineNumber(bus.getLineNumber());
        dto.setDriverName(bus.getDriverName());
        dto.setDriverPhone(bus.getDriverPhone());
        dto.setTotalSeats(bus.getTotalSeats());
        dto.setAvailableSeats(bus.getAvailableSeats());
        dto.setCurrentLocationLat(bus.getCurrentLocationLat());
        dto.setCurrentLocationLng(bus.getCurrentLocationLng());
        dto.setStatus(bus.getStatus());
        dto.setDepartureTime(bus.getDepartureTime());
        dto.setArrivalTime(bus.getArrivalTime());
        dto.setRouteDescription(bus.getRouteDescription());
        dto.setCreatedAt(bus.getCreatedAt());
        dto.setUpdatedAt(bus.getUpdatedAt());
        
        return dto;
    }
}
