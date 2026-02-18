package com.isep.repository;

import com.isep.model.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    
    Optional<School> findByCode(String code);
    
    Optional<School> findByEmail(String email);
    
    boolean existsByCode(String code);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT s FROM School s WHERE s.isActive = true AND s.isSubscriptionActive() = true")
    List<School> findActiveSchoolsWithValidSubscription();
    
    @Query("SELECT s FROM School s WHERE s.isActive = true AND s.subscriptionEnd <= :date")
    List<School> findSchoolsWithExpiringSubscription(@Param("date") java.time.LocalDateTime date);
    
    @Query("SELECT s FROM School s WHERE s.isActive = true AND s.canAddMoreStudents() = false")
    List<School> findSchoolsAtCapacity();
    
    @Query("SELECT COUNT(s) FROM School s WHERE s.isActive = true AND s.isSubscriptionActive() = true")
    long countActiveSchoolsWithValidSubscription();
    
    @Query("SELECT s FROM School s WHERE s.isActive = true ORDER BY s.createdAt DESC")
    Page<School> findActiveSchools(Pageable pageable);
    
    @Query("SELECT s FROM School s WHERE s.subscriptionPlan = :plan AND s.isActive = true")
    List<School> findBySubscriptionPlan(@Param("plan") School.SubscriptionPlan plan);
    
    @Query("SELECT s FROM School s WHERE s.city = :city AND s.isActive = true")
    List<School> findByCity(@Param("city") String city);
    
    @Query("SELECT s FROM School s WHERE s.country = :country AND s.isActive = true")
    List<School> findByCountry(@Param("country") String country);
}
