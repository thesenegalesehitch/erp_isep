package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Enrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String enrollmentId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentType enrollmentType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;
    
    @Column(nullable = false)
    private LocalDate enrollmentDate;
    
    private LocalDate validationDate;
    
    private LocalDate cancellationDate;
    
    private String cancellationReason;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    @Column(nullable = false)
    private BigDecimal tuitionFee;
    
    private BigDecimal scholarshipAmount;
    
    private BigDecimal paidAmount;
    
    private LocalDate lastPaymentDate;
    
    private String paymentReference;
    
    @Column(nullable = false)
    private String academicYear;
    
    private String specialNotes;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public enum EnrollmentType {
        FULL_TIME,
        PART_TIME,
        ONLINE,
        DISTANCE_LEARNING,
        APPRENTICESHIP,
        INTERNSHIP
    }
    
    public enum EnrollmentStatus {
        PENDING,
        APPROVED,
        REJECTED,
        ENROLLED,
        ON_LEAVE,
        GRADUATED,
        CANCELLED,
        SUSPENDED
    }
    
    public enum PaymentStatus {
        PENDING,
        PARTIAL,
        PAID,
        OVERDUE,
        SCHOLARSHIP,
        REFUNDED
    }
    
    public BigDecimal getRemainingAmount() {
        return tuitionFee.subtract(paidAmount != null ? paidAmount : BigDecimal.ZERO)
                        .subtract(scholarshipAmount != null ? scholarshipAmount : BigDecimal.ZERO);
    }
    
    public boolean isFullyPaid() {
        return paymentStatus == PaymentStatus.PAID || 
               (paidAmount != null && paidAmount.add(scholarshipAmount != null ? scholarshipAmount : BigDecimal.ZERO)
                                       .compareTo(tuitionFee) >= 0);
    }
    
    public boolean isOverdue() {
        return paymentStatus == PaymentStatus.OVERDUE || 
               (paymentStatus == PaymentStatus.PARTIAL && 
                lastPaymentDate != null && 
                lastPaymentDate.isBefore(LocalDate.now().minusMonths(3)));
    }
}
