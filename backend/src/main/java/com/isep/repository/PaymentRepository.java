package com.isep.repository;

import com.isep.model.Payment;
import com.isep.model.Payment.PaymentStatus;
import com.isep.model.Payment.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    List<Payment> findByParentId(Long parentId);
    
    List<Payment> findByStudentId(Long studentId);
    
    List<Payment> findBySchoolId(Long schoolId);
    
    List<Payment> findByStatus(PaymentStatus status);
    
    List<Payment> findByPaymentType(PaymentType paymentType);
    
    @Query("SELECT p FROM Payment p WHERE p.parent.id = :parentId AND p.status = 'PENDING' AND p.dueDate < :now")
    List<Payment> findOverduePaymentsByParent(@Param("parentId") Long parentId, @Param("now") LocalDateTime now);
    
    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' AND p.dueDate BETWEEN :start AND :end")
    List<Payment> findUpcomingPayments(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.parent.id = :parentId AND p.status = 'COMPLETED' AND p.paymentDate BETWEEN :start AND :end")
    BigDecimal getTotalPaymentsByParentInPeriod(@Param("parentId") Long parentId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = 'COMPLETED' AND p.paymentDate BETWEEN :start AND :end")
    long countCompletedPaymentsInPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
