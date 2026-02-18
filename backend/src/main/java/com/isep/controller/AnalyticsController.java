package com.isep.controller;

import com.isep.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getDashboardAnalytics() {
        return ResponseEntity.ok(analyticsService.getDashboardAnalytics());
    }
    
    @GetMapping("/schools/{schoolId}")
    @PreAuthorize("hasRole('ADMIN') or @schoolService.isSchoolAdmin(#schoolId, authentication.name)")
    public ResponseEntity<Map<String, Object>> getSchoolAnalytics(@PathVariable Long schoolId) {
        return ResponseEntity.ok(analyticsService.getSchoolAnalytics(schoolId));
    }
    
    @GetMapping("/schools/{schoolId}/engagement")
    @PreAuthorize("hasRole('ADMIN') or @schoolService.isSchoolAdmin(#schoolId, authentication.name)")
    public ResponseEntity<Map<String, Object>> getSchoolEngagementMetrics(@PathVariable Long schoolId) {
        return ResponseEntity.ok(analyticsService.getSchoolEngagementMetrics(schoolId));
    }
    
    @GetMapping("/schools/{schoolId}/usage")
    @PreAuthorize("hasRole('ADMIN') or @schoolService.isSchoolAdmin(#schoolId, authentication.name)")
    public ResponseEntity<Map<String, Object>> getSchoolUsageMetrics(@PathVariable Long schoolId) {
        return ResponseEntity.ok(analyticsService.getSchoolUsageMetrics(schoolId));
    }
    
    @GetMapping("/schools/{schoolId}/activity")
    @PreAuthorize("hasRole('ADMIN') or @schoolService.isSchoolAdmin(#schoolId, authentication.name)")
    public ResponseEntity<Map<String, Object>> getSchoolActivityMetrics(
            @PathVariable Long schoolId,
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(analyticsService.getSchoolActivityMetrics(schoolId, days));
    }
    
    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getRevenueAnalytics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : LocalDateTime.now().minusMonths(12);
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : LocalDateTime.now();
        return ResponseEntity.ok(analyticsService.getRevenueAnalytics(start, end));
    }
    
    @GetMapping("/growth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getGrowthMetrics() {
        return ResponseEntity.ok(analyticsService.getGrowthMetrics());
    }
}
