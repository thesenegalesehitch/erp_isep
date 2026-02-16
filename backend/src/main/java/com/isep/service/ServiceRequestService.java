package com.isep.service;

import com.isep.model.Service;
import com.isep.model.ServiceRequest;
import com.isep.model.User;
import com.isep.repository.ServiceRepository;
import com.isep.repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {
    
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRepository serviceRepository;
    private final NotificationService notificationService;
    
    @Transactional
    public ServiceRequest createRequest(Long serviceId, Long requesterId, String message, LocalDateTime requestedDate) {
        Service service = serviceRepository.findById(serviceId)
            .orElseThrow(() -> new RuntimeException("Service not found"));
        
        User requester = new User();
        requester.setId(requesterId);
        
        ServiceRequest request = new ServiceRequest();
        request.setService(service);
        request.setRequester(requester);
        request.setMessage(message);
        request.setRequestedDate(requestedDate);
        request.setStatus(ServiceRequest.RequestStatus.PENDING);
        
        request = serviceRequestRepository.save(request);
        
        // Notify service provider
        notificationService.notifyServiceRequest(
            service.getProvider().getId(),
            service.getId(),
            service.getTitle()
        );
        
        return request;
    }
    
    @Transactional
    public ServiceRequest updateStatus(Long requestId, ServiceRequest.RequestStatus status) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus(status);
        return serviceRequestRepository.save(request);
    }
    
    public List<ServiceRequest> getRequestsForService(Long serviceId) {
        return serviceRequestRepository.findByServiceId(serviceId);
    }
    
    public List<ServiceRequest> getRequestsByUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return serviceRequestRepository.findByRequester(user);
    }
    
    public List<ServiceRequest> getRequestsForProvider(Long providerId) {
        return serviceRequestRepository.findByServiceProviderId(providerId);
    }
}
