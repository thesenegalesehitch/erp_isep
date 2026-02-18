package com.isep.dto;

import com.isep.model.Payment;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private Long parentId;
    private Long studentId;
    private String studentName;
    private Long schoolId;
    private String schoolName;
    private BigDecimal amount;
    private String currency;
    private Payment.PaymentType paymentType;
    private String description;
    private String transactionId;
    private String paymentMethod;
    private Payment.PaymentStatus status;
    private LocalDateTime paymentDate;
    private LocalDateTime dueDate;
    private BigDecimal lateFee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
