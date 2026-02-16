package com.isep.repository;

import com.isep.model.Service;
import com.isep.model.ServiceRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRatingRepository extends JpaRepository<ServiceRating, Long> {
    List<ServiceRating> findByService(Service service);
    Optional<ServiceRating> findByServiceIdAndUserId(Long serviceId, Long userId);
    List<ServiceRating> findByUserId(Long userId);
    Double calculateAverageRating(Long serviceId);
}
