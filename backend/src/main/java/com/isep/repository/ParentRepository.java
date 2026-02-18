package com.isep.repository;

import com.isep.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    
    Optional<Parent> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT p FROM Parent p WHERE p.subscriptionEnd IS NULL OR p.subscriptionEnd > CURRENT_TIMESTAMP")
    List<Parent> findActiveSubscribers();
    
    @Query("SELECT p FROM Parent p WHERE p.subscriptionEnd < CURRENT_TIMESTAMP")
    List<Parent> findExpiredSubscriptions();
    
    @Query("SELECT COUNT(p) FROM Parent p WHERE p.subscriptionType = 'PREMIUM' AND (p.subscriptionEnd IS NULL OR p.subscriptionEnd > CURRENT_TIMESTAMP)")
    long countActivePremiumSubscribers();
    
    @Query("SELECT p FROM Parent p JOIN p.studentLinks sl WHERE sl.student.id = :studentId")
    List<Parent> findByStudentId(@Param("studentId") Long studentId);
}
