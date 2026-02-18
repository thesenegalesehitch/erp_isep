package com.isep.service;

import com.isep.dto.SubscriptionDTO;
import com.isep.model.School;
import com.isep.model.Subscription;
import com.isep.repository.SchoolRepository;
import com.isep.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {
    
    private final SubscriptionRepository subscriptionRepository;
    private final SchoolRepository schoolRepository;
    
    public List<SubscriptionDTO> getSchoolSubscriptions(Long schoolId) {
        return subscriptionRepository.findAllBySchoolIdOrderByCreatedAtDesc(schoolId)
                .stream()
                .map(SubscriptionDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public SubscriptionDTO createSubscription(Long schoolId, SubscriptionDTO subscriptionDTO) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School not found"));
        
        Subscription subscription = Subscription.builder()
                .school(school)
                .plan(subscriptionDTO.getPlan())
                .startDate(subscriptionDTO.getStartDate())
                .endDate(subscriptionDTO.getEndDate())
                .amount(subscriptionDTO.getAmount())
                .currency(subscriptionDTO.getCurrency())
                .paymentStatus(Subscription.PaymentStatus.PENDING)
                .paymentMethod(subscriptionDTO.getPaymentMethod())
                .transactionId(UUID.randomUUID().toString())
                .build();
        
        subscription = subscriptionRepository.save(subscription);
        
        school.setSubscriptionPlan(subscriptionDTO.getPlan());
        school.setSubscriptionStart(subscriptionDTO.getStartDate());
        school.setSubscriptionEnd(subscriptionDTO.getEndDate());
        school.setMaxStudents(subscriptionDTO.getPlan().getMaxStudents());
        schoolRepository.save(school);
        
        return SubscriptionDTO.fromEntity(subscription);
    }
    
    public void completePayment(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        
        subscription.setPaymentStatus(Subscription.PaymentStatus.COMPLETED);
        subscriptionRepository.save(subscription);
    }
    
    public List<Subscription> getExpiringSubscriptions() {
        return subscriptionRepository.findExpiringActiveSubscriptions(LocalDateTime.now().plusDays(30));
    }
}
