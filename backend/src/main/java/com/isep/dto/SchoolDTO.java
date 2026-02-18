package com.isep.dto;

import com.isep.model.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDTO {
    
    private Long id;
    private String name;
    private String code;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private School.SubscriptionPlan subscriptionPlan;
    private LocalDateTime subscriptionStart;
    private LocalDateTime subscriptionEnd;
    private Boolean isActive;
    private Integer maxStudents;
    private Integer currentStudentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static SchoolDTO fromEntity(School school) {
        if (school == null) return null;
        
        return SchoolDTO.builder()
                .id(school.getId())
                .name(school.getName())
                .code(school.getCode())
                .email(school.getEmail())
                .phone(school.getPhone())
                .address(school.getAddress())
                .city(school.getCity())
                .country(school.getCountry())
                .subscriptionPlan(school.getSubscriptionPlan())
                .subscriptionStart(school.getSubscriptionStart())
                .subscriptionEnd(school.getSubscriptionEnd())
                .isActive(school.getIsActive())
                .maxStudents(school.getMaxStudents())
                .currentStudentCount(school.getCurrentStudentCount())
                .createdAt(school.getCreatedAt())
                .updatedAt(school.getUpdatedAt())
                .build();
    }
}
