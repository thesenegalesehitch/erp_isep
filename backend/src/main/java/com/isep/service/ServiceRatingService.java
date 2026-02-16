package com.isep.service;

import com.isep.model.Service;
import com.isep.model.ServiceRating;
import com.isep.model.User;
import com.isep.repository.ServiceRatingRepository;
import com.isep.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRatingService {
    
    private final ServiceRatingRepository serviceRatingRepository;
    private final ServiceRepository serviceRepository;
    
    @Transactional
    public ServiceRating createRating(Long serviceId, Long userId, Integer rating, String comment) {
        // Check if user already rated this service
        if (serviceRatingRepository.findByServiceIdAndUserId(serviceId, userId).isPresent()) {
            throw new RuntimeException("User has already rated this service");
        }
        
        Service service = serviceRepository.findById(serviceId)
            .orElseThrow(() -> new RuntimeException("Service not found"));
        
        User user = new User();
        user.setId(userId);
        
        ServiceRating serviceRating = new ServiceRating();
        serviceRating.setService(service);
        serviceRating.setUser(user);
        serviceRating.setRating(rating);
        serviceRating.setComment(comment);
        
        serviceRating = serviceRatingRepository.save(serviceRating);
        
        // Update service average rating
        updateServiceAverageRating(serviceId);
        
        return serviceRating;
    }
    
    private void updateServiceAverageRating(Long serviceId) {
        List<ServiceRating> ratings = serviceRatingRepository.findByServiceId(serviceId);
        
        if (ratings.isEmpty()) {
            return;
        }
        
        double average = ratings.stream()
            .mapToInt(ServiceRating::getRating)
            .average()
            .orElse(0.0);
        
        Service service = serviceRepository.findById(serviceId).orElseThrow();
        service.setAverageRating(average);
        service.setTotalRatings(ratings.size());
        serviceRepository.save(service);
    }
    
    public List<ServiceRating> getRatingsForService(Long serviceId) {
        return serviceRatingRepository.findByServiceId(serviceId);
    }
    
    public ServiceRating getUserRatingForService(Long serviceId, Long userId) {
        return serviceRatingRepository.findByServiceIdAndUserId(serviceId, userId).orElse(null);
    }
}
