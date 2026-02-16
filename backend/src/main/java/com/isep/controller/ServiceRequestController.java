package com.isep.controller;

import com.isep.dto.ServiceRequestDTO;
import com.isep.model.ServiceRequest;
import com.isep.repository.ServiceRequestRepository;
import com.isep.repository.ServiceRepository;
import com.isep.repository.UserRepository;
import com.isep.service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/service-requests")
@RequiredArgsConstructor
public class ServiceRequestController {
    
    private final ServiceRequestService serviceRequestService;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    
    @PostMapping
    public ResponseEntity<ServiceRequestDTO> createRequest(
            @RequestParam Long serviceId,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) LocalDateTime requestedDate,
            Authentication authentication) {
        
        Long userId = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"))
            .getId();
        
        ServiceRequest request = serviceRequestService.createRequest(
            serviceId, userId, message, requestedDate
        );
        
        return ResponseEntity.ok(ServiceRequestDTO.fromEntity(request));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceRequestDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam ServiceRequest.RequestStatus status) {
        
        ServiceRequest request = serviceRequestService.updateStatus(id, status);
        return ResponseEntity.ok(ServiceRequestDTO.fromEntity(request));
    }
    
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ServiceRequestDTO>> getRequestsForService(@PathVariable Long serviceId) {
        List<ServiceRequestDTO> requests = serviceRequestService.getRequestsForService(serviceId)
            .stream()
            .map(ServiceRequestDTO::fromEntity)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(requests);
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<ServiceRequestDTO>> getRequestsByUser(Authentication authentication) {
        Long userId = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"))
            .getId();
        
        List<ServiceRequestDTO> requests = serviceRequestService.getRequestsByUser(userId)
            .stream()
            .map(ServiceRequestDTO::fromEntity)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(requests);
    }
}
