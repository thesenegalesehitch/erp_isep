package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "schools")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class School {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String country;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolType schoolType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolSize schoolSize;
    
    @Column(nullable = false)
    private Boolean hasDormitories = false;
    
    @Column(nullable = false)
    private Boolean hasRestaurant = false;
    
    @Column(nullable = false)
    private Boolean hasResearchLab = false;
    
    @Column(nullable = false)
    private Boolean hasEnterprisePartnership = false;
    
    @Column(nullable = false)
    private LocalDateTime licenseStart;
    
    private LocalDateTime licenseEnd;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Integer maxStudents;
    
    private Integer currentStudentCount = 0;
    
    @Column(nullable = false)
    private String academicYear;
    
    @Column(nullable = false)
    private String rectorName;
    
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
    
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> activities;
    
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Announcement> announcements;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public enum SchoolType {
        PUBLIC_UNIVERSITY,
        PRIVATE_UNIVERSITY,
        PROFESSIONAL_INSTITUTE,
        TECHNICAL_SCHOOL,
        BUSINESS_SCHOOL
    }
    
    public enum SchoolSize {
        SMALL(500),      // < 500 étudiants
        MEDIUM(2000),    // 500-2000 étudiants
        LARGE(10000),    // 2000-10000 étudiants
        VERY_LARGE(50000); // > 10000 étudiants
        
        private final int maxStudents;
        
        SchoolSize(int maxStudents) {
            this.maxStudents = maxStudents;
        }
        
        public int getMaxStudents() {
            return maxStudents;
        }
    }
    
    public boolean isLicenseActive() {
        return isActive && (licenseEnd == null || licenseEnd.isAfter(LocalDateTime.now()));
    }
    
    public boolean canAddMoreStudents() {
        return currentStudentCount < maxStudents;
    }
    
    public boolean hasModule(String module) {
        return switch (module.toLowerCase()) {
            case "dormitories" -> hasDormitories;
            case "restaurant" -> hasRestaurant;
            case "research" -> hasResearchLab;
            case "enterprise" -> hasEnterprisePartnership;
            default -> false;
        };
    }
}
