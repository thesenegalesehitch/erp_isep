package com.isep.controller;

import com.isep.dto.ServiceRatingDTO;
import com.isep.model.ServiceRating;
import com.isep.repository.ServiceRatingRepository;
import com.isep.repository.ServiceRepository;
import com.isep.repository.UserRepository;
import com.isep.service.ServiceRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/service-ratings")
@RequiredArgsConstructor
public class ServiceRatingController {
    
    private final ServiceRatingService serviceRatingService;
    private final ServiceRatingRepository serviceRatingRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity<ServiceRatingDTO> createRating(
            @RequestParam Long serviceId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment,
            Authentication authentication) {
        
        Long userId = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"))
            .getId();
        
        ServiceRating ratingObj = serviceRatingService.createRating(serviceId, userId, rating, comment);
        
        return ResponseEntity.ok(ServiceRatingDTO.fromEntity(ratingObj));
    }
    
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ServiceRatingDTO>> getRatingsForService(@PathVariable Long serviceId) {
        List<ServiceRatingDTO> ratings = serviceRatingService.getRatingsForService(serviceId)
            .stream()
            .map(ServiceRatingDTO::fromEntity)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(ratings);
    }
    
    @GetMapping("/service/{serviceId}/user")
    public ResponseEntity<ServiceRatingDTO> getUserRatingForService(
            @PathVariable Long serviceId,
            Authentication authentication) {
        
        Long userId = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"))
            .getId();
        
        ServiceRating rating = serviceRatingService.getUserRatingForService(serviceId, userId);
        
        if (rating == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(ServiceRatingDTO.fromEntity(rating));
    }
}
