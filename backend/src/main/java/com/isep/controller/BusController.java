package com.isep.controller;

import com.isep.model.Bus;
import com.isep.model.BusReservation;
import com.isep.model.User;
import com.isep.repository.BusRepository;
import com.isep.repository.BusReservationRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
@RequiredArgsConstructor
public class BusController {
    
    private final BusRepository busRepository;
    private final BusReservationRepository busReservationRepository;
    private final UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<Bus>> getAllBuses() {
        return ResponseEntity.ok(busRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        return busRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<BusReservation>> getBusReservations(@PathVariable Long id) {
        Bus bus = busRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(busReservationRepository.findByBus(bus));
    }
    
    @PostMapping("/{id}/reserve")
    public ResponseEntity<BusReservation> reserveBus(
            @PathVariable Long id,
            @Valid @RequestBody BusReservation reservation,
            Authentication authentication) {
        Bus bus = busRepository.findById(id).orElseThrow();
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow();
        
        if (bus.getAvailableSeats() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        
        reservation.setBus(bus);
        reservation.setUser(user);
        return ResponseEntity.ok(busReservationRepository.save(reservation));
    }
}

