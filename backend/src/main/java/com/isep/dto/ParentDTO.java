package com.isep.dto;

import com.isep.model.Parent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParentDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String profilePhoto;
    private Boolean isActive;
    private Boolean emailVerified;
    private Parent.SubscriptionType subscriptionType;
    private LocalDateTime subscriptionStart;
    private LocalDateTime subscriptionEnd;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
