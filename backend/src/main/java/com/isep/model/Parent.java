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
@Table(name = "parents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Parent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "profile_photo")
    private String profilePhoto;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "email_verified")
    private Boolean emailVerified = false;
    
    @Column(name = "email_verification_token")
    private String emailVerificationToken;
    
    @Column(name = "subscription_type")
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType = SubscriptionType.FREE;
    
    @Column(name = "subscription_start")
    private LocalDateTime subscriptionStart;
    
    @Column(name = "subscription_end")
    private LocalDateTime subscriptionEnd;
    
    @Column(name = "payment_method")
    private String paymentMethod;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentLink> studentLinks = new HashSet<>();
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> sentMessages = new HashSet<>();
    
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> receivedMessages = new HashSet<>();
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum SubscriptionType {
        FREE(0, 1),
        PREMIUM(10, 5);
        
        private final int monthlyPrice;
        private final int maxStudents;
        
        SubscriptionType(int monthlyPrice, int maxStudents) {
            this.monthlyPrice = monthlyPrice;
            this.maxStudents = maxStudents;
        }
        
        public int getMonthlyPrice() {
            return monthlyPrice;
        }
        
        public int getMaxStudents() {
            return maxStudents;
        }
    }
    
    public boolean isSubscriptionActive() {
        return subscriptionEnd == null || subscriptionEnd.isAfter(LocalDateTime.now());
    }
    
    public boolean canLinkMoreStudents() {
        return studentLinks.size() < subscriptionType.getMaxStudents();
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
