package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Service {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceCategory category;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "is_available")
    private Boolean isAvailable = true;
    
    @Column(name = "average_rating")
    private Double averageRating = 0.0;
    
    @Column(name = "total_ratings")
    private Integer totalRatings = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider;
    
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ServiceRequest> requests = new HashSet<>();
    
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ServiceRating> ratings = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(name = "service_images", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "image_url")
    private Set<String> images = new HashSet<>();
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum ServiceCategory {
        MECHANICS,
        ELECTRICAL,
        PLUMBING,
        IT,
        TUTORING,
        DESIGN,
        OTHER
    }
}

