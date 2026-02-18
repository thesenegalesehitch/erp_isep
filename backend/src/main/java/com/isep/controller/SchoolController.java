package com.isep.controller;

import com.isep.dto.SchoolDTO;
import com.isep.dto.SubscriptionDTO;
import com.isep.model.School;
import com.isep.model.Subscription;
import com.isep.service.SchoolService;
import com.isep.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {
    
    private final SchoolService schoolService;
    private final SubscriptionService subscriptionService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<SchoolDTO>> getAllSchools(Pageable pageable) {
        return ResponseEntity.ok(schoolService.getAllSchools(pageable));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @schoolService.isSchoolMember(#id, authentication.name)")
    public ResponseEntity<SchoolDTO> getSchool(@PathVariable Long id) {
        return ResponseEntity.ok(schoolService.getSchoolById(id));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SchoolDTO> createSchool(@Valid @RequestBody SchoolDTO schoolDTO) {
        return ResponseEntity.ok(schoolService.createSchool(schoolDTO));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @schoolService.isSchoolAdmin(#id, authentication.name)")
    public ResponseEntity<SchoolDTO> updateSchool(@PathVariable Long id, @Valid @RequestBody SchoolDTO schoolDTO) {
        return ResponseEntity.ok(schoolService.updateSchool(id, schoolDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/subscriptions")
    @PreAuthorize("hasRole('ADMIN') or @schoolService.isSchoolAdmin(#id, authentication.name)")
    public ResponseEntity<List<SubscriptionDTO>> getSchoolSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getSchoolSubscriptions(id));
    }
    
    @PostMapping("/{id}/subscriptions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubscriptionDTO> createSubscription(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.ok(subscriptionService.createSubscription(id, subscriptionDTO));
    }
    
    @GetMapping("/{id}/analytics")
    @PreAuthorize("hasRole('ADMIN') or @schoolService.isSchoolAdmin(#id, authentication.name)")
    public ResponseEntity<?> getSchoolAnalytics(@PathVariable Long id) {
        return ResponseEntity.ok(schoolService.getSchoolAnalytics(id));
    }
    
    @GetMapping("/dashboard/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getDashboardStats() {
        return ResponseEntity.ok(schoolService.getDashboardStats());
    }
}
