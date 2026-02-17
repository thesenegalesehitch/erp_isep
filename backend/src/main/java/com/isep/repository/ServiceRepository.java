package com.isep.repository;

import com.isep.model.Service;
import com.isep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByProvider(User provider);
    List<Service> findByCategory(Service.ServiceCategory category);
    List<Service> findByIsAvailableTrue();
    
    @Query("SELECT s FROM Service s WHERE s.isAvailable = true AND " +
           "(:category IS NULL OR s.category = :category) AND " +
           "(:location IS NULL OR s.location LIKE %:location%)")
    List<Service> searchServices(@Param("category") Service.ServiceCategory category, 
                                 @Param("location") String location);
                                 
    @Query("SELECT s FROM Service s WHERE s.isAvailable = true AND (LOWER(s.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Service> searchByQuery(@Param("query") String query);
}

