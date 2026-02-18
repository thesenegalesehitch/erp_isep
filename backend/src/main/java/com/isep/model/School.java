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
    private SubscriptionPlan subscriptionPlan;
    
    @Column(nullable = false)
    private LocalDateTime subscriptionStart;
    
    private LocalDateTime subscriptionEnd;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Integer maxStudents;
    
    private Integer currentStudentCount = 0;
    
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
    
    public enum SubscriptionPlan {
        BASIC(50, 200),      // 50€/mois, 200 étudiants max
        PREMIUM(100, 500),   // 100€/mois, 500 étudiants max
        ENTERPRISE(200, 2000); // 200€/mois, 2000 étudiants max
        
        private final int monthlyPrice;
        private final int maxStudents;
        
        SubscriptionPlan(int monthlyPrice, int maxStudents) {
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
        return isActive && (subscriptionEnd == null || subscriptionEnd.isAfter(LocalDateTime.now()));
    }
    
    public boolean canAddMoreStudents() {
        return currentStudentCount < maxStudents;
    }
}
