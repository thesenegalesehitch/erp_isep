package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "student_number", unique = true)
    private String studentNumber;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.STUDENT;
    
    @Column(name = "specialty")
    private String specialty;
    
    @Column(name = "profile_photo")
    private String profilePhoto;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "email_verified")
    private Boolean emailVerified = false;
    
    @Column(name = "email_verification_token")
    private String emailVerificationToken;
    
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Service> services = new HashSet<>();
    
    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ServiceRequest> serviceRequests = new HashSet<>();
    
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> sentMessages = new HashSet<>();
    
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> receivedMessages = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusReservation> busReservations = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ActivityRegistration> activityRegistrations = new HashSet<>();
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum Role {
        STUDENT,
        TEACHER,
        ADMIN
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}

