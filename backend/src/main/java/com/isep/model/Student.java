package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String studentId;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    @Column(nullable = false)
    private String birthPlace;
    
    @Column(nullable = false)
    private String nationality;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String country;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    
    @Column(nullable = false)
    private LocalDate enrollmentDate;
    
    private LocalDate graduationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyLevel studyLevel;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus enrollmentStatus;
    
    @Column(nullable = false)
    private String department;
    
    @Column(nullable = false)
    private String program;
    
    private String specialization;
    
    @Column(nullable = false)
    private String academicYear;
    
    private Boolean hasScholarship = false;
    
    private String scholarshipType;
    
    private Boolean hasDormitory = false;
    
    private String dormitoryRoom;
    
    private Boolean hasMealPlan = false;
    
    private String mealPlanType;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
    
    public enum StudyLevel {
        BACHELOR_1,
        BACHELOR_2,
        BACHELOR_3,
        MASTER_1,
        MASTER_2,
        DOCTORATE_1,
        DOCTORATE_2,
        DOCTORATE_3,
        PROFESSIONAL_CERTIFICATE,
        TECHNICAL_CERTIFICATE
    }
    
    public enum EnrollmentStatus {
        ENROLLED,
        ON_LEAVE,
        GRADUATED,
        SUSPENDED,
        WITHDRAWN,
        DECEASED
    }
    
    public boolean isCurrentlyEnrolled() {
        return isActive && enrollmentStatus == EnrollmentStatus.ENROLLED;
    }
    
    public int getStudyYear() {
        return switch (studyLevel) {
            case BACHELOR_1, MASTER_1, DOCTORATE_1, PROFESSIONAL_CERTIFICATE, TECHNICAL_CERTIFICATE -> 1;
            case BACHELOR_2, MASTER_2, DOCTORATE_2 -> 2;
            case BACHELOR_3, DOCTORATE_3 -> 3;
            default -> 0;
        };
    }
}
