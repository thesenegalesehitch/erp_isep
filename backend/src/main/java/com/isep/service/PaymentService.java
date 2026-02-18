package com.isep.service;

import com.isep.model.Payment;
import com.isep.model.Parent;
import com.isep.model.School;
import com.isep.model.User;
import com.isep.repository.PaymentRepository;
import com.isep.repository.ParentRepository;
import com.isep.repository.SchoolRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final ParentRepository parentRepository;
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    
    public Payment createPayment(Payment payment) {
        Parent parent = parentRepository.findById(payment.getParent().getId())
            .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        User student = userRepository.findById(payment.getStudent().getId())
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        School school = schoolRepository.findById(payment.getSchool().getId())
            .orElseThrow(() -> new RuntimeException("School not found"));
        
        payment.setParent(parent);
        payment.setStudent(student);
        payment.setSchool(school);
        payment.setTransactionId(generateTransactionId());
        payment.setStatus(Payment.PaymentStatus.PENDING);
        
        if (payment.getDueDate() == null) {
            payment.setDueDate(LocalDateTime.now().plusDays(30));
        }
        
        Payment savedPayment = paymentRepository.save(payment);
        
        notificationService.sendPaymentRequest(savedPayment);
        
        log.info("Payment created: {} for parent {} - student {}", 
                savedPayment.getId(), parent.getId(), student.getId());
        return savedPayment;
    }
    
    public Payment processPayment(Long paymentId, String paymentMethod) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        if (payment.getStatus() != Payment.PaymentStatus.PENDING) {
            throw new RuntimeException("Payment cannot be processed");
        }
        
        payment.setStatus(Payment.PaymentStatus.PROCESSING);
        payment.setPaymentMethod(paymentMethod);
        paymentRepository.save(payment);
        
        try {
            boolean paymentSuccessful = processExternalPayment(payment);
            
            if (paymentSuccessful) {
                payment.setStatus(Payment.PaymentStatus.COMPLETED);
                payment.setPaymentDate(LocalDateTime.now());
                notificationService.sendPaymentConfirmation(payment);
                log.info("Payment completed successfully: {}", paymentId);
            } else {
                payment.setStatus(Payment.PaymentStatus.FAILED);
                notificationService.sendPaymentFailure(payment);
                log.error("Payment failed: {}", paymentId);
            }
            
        } catch (Exception e) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            log.error("Payment processing error: {}", paymentId, e);
        }
        
        return paymentRepository.save(payment);
    }
    
    public List<Payment> getOverduePayments(Long parentId) {
        return paymentRepository.findOverduePaymentsByParent(parentId, LocalDateTime.now());
    }
    
    public List<Payment> getUpcomingPayments(int daysAhead) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(daysAhead);
        return paymentRepository.findUpcomingPayments(start, end);
    }
    
    public Page<Payment> getParentPayments(Long parentId, Pageable pageable) {
        return paymentRepository.findByParentId(parentId, pageable);
    }
    
    @Transactional(readOnly = true)
    public BigDecimal getTotalPaymentsByParentInPeriod(Long parentId, LocalDateTime start, LocalDateTime end) {
        return paymentRepository.getTotalPaymentsByParentInPeriod(parentId, start, end);
    }
    
    @Transactional(readOnly = true)
    public List<Payment> getSchoolPayments(Long schoolId) {
        return paymentRepository.findBySchoolId(schoolId);
    }
    
    public Payment refundPayment(Long paymentId, String reason) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
            throw new RuntimeException("Only completed payments can be refunded");
        }
        
        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        Payment savedPayment = paymentRepository.save(payment);
        
        notificationService.sendRefundNotification(savedPayment, reason);
        
        log.info("Payment refunded: {} - Reason: {}", paymentId, reason);
        return savedPayment;
    }
    
    public void applyLateFees() {
        List<Payment> overduePayments = paymentRepository.findOverduePaymentsByParent(null, LocalDateTime.now());
        
        for (Payment payment : overduePayments) {
            if (payment.getLateFee() == null || payment.getLateFee().compareTo(BigDecimal.ZERO) == 0) {
                BigDecimal lateFee = payment.getAmount().multiply(BigDecimal.valueOf(0.05));
                payment.setLateFee(lateFee);
                paymentRepository.save(payment);
                
                notificationService.sendLateFeeNotification(payment);
                log.info("Late fee applied to payment: {}", paymentId);
            }
        }
    }
    
    private boolean processExternalPayment(Payment payment) {
        return true;
    }
    
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
