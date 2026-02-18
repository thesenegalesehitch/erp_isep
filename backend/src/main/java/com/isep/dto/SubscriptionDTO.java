package com.isep.dto;

import com.isep.model.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    
    private Long id;
    private Long schoolId;
    private String schoolName;
    private Subscription.Plan plan;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer amount;
    private String currency;
    private Subscription.PaymentStatus paymentStatus;
    private String paymentMethod;
    private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    
    public static SubscriptionDTO fromEntity(Subscription subscription) {
        if (subscription == null) return null;
        
        return SubscriptionDTO.builder()
                .id(subscription.getId())
                .schoolId(subscription.getSchool() != null ? subscription.getSchool().getId() : null)
                .schoolName(subscription.getSchool() != null ? subscription.getSchool().getName() : null)
                .plan(subscription.getPlan())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .amount(subscription.getAmount())
                .currency(subscription.getCurrency())
                .paymentStatus(subscription.getPaymentStatus())
                .paymentMethod(subscription.getPaymentMethod())
                .transactionId(subscription.getTransactionId())
                .createdAt(subscription.getCreatedAt())
                .updatedAt(subscription.getUpdatedAt())
                .isActive(subscription.isActive())
                .build();
    }
}
