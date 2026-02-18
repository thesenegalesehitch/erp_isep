package com.isep.repository;

import com.isep.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    List<Subscription> findBySchoolId(Long schoolId);
    
    @Query("SELECT s FROM Subscription s WHERE s.school.id = :schoolId AND s.isActive() = true")
    Optional<Subscription> findActiveSubscriptionBySchoolId(@Param("schoolId") Long schoolId);
    
    @Query("SELECT s FROM Subscription s WHERE s.school.id = :schoolId ORDER BY s.createdAt DESC")
    List<Subscription> findAllBySchoolIdOrderByCreatedAtDesc(@Param("schoolId") Long schoolId);
    
    @Query("SELECT s FROM Subscription s WHERE s.endDate <= :date AND s.paymentStatus = 'COMPLETED'")
    List<Subscription> findExpiringSubscriptions(@Param("date") LocalDateTime date);
    
    @Query("SELECT s FROM Subscription s WHERE s.endDate <= :date AND s.paymentStatus = 'COMPLETED' AND s.school.isActive = true")
    List<Subscription> findExpiringActiveSubscriptions(@Param("date") LocalDateTime date);
    
    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.paymentStatus = 'COMPLETED' AND s.startDate <= :now AND s.endDate >= :now")
    long countActiveSubscriptions(@Param("now") LocalDateTime now);
    
    @Query("SELECT s FROM Subscription s WHERE s.paymentStatus = :status")
    List<Subscription> findByPaymentStatus(@Param("status") Subscription.PaymentStatus status);
    
    @Query("SELECT s FROM Subscription s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    List<Subscription> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
