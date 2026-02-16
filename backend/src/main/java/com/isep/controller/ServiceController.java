package com.isep.controller;

import com.isep.model.Service;
import com.isep.model.User;
import com.isep.repository.ServiceRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<Service>> getAllServices(
            @RequestParam(required = false) Service.ServiceCategory category,
            @RequestParam(required = false) String location) {
        List<Service> services = category != null || location != null
            ? serviceRepository.searchServices(category, location)
            : serviceRepository.findByIsAvailableTrue();
        return ResponseEntity.ok(services);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Service> getService(@PathVariable Long id) {
        return serviceRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Service> createService(
            @Valid @RequestBody Service service,
            Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow();
        service.setProvider(user);
        return ResponseEntity.ok(serviceRepository.save(service));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(
            @PathVariable Long id,
            @Valid @RequestBody Service serviceDetails,
            Authentication authentication) {
        return serviceRepository.findById(id)
            .map(service -> {
                User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow();
                if (!service.getProvider().getId().equals(user.getId())) {
                    return ResponseEntity.<Service>forbidden().build();
                }
                service.setTitle(serviceDetails.getTitle());
                service.setDescription(serviceDetails.getDescription());
                service.setCategory(serviceDetails.getCategory());
                service.setPrice(serviceDetails.getPrice());
                service.setLocation(serviceDetails.getLocation());
                service.setIsAvailable(serviceDetails.getIsAvailable());
                return ResponseEntity.ok(serviceRepository.save(service));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long id,
            Authentication authentication) {
        return serviceRepository.findById(id)
            .map(service -> {
                User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow();
                if (!service.getProvider().getId().equals(user.getId())) {
                    return ResponseEntity.<Void>forbidden().build();
                }
                serviceRepository.delete(service);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

