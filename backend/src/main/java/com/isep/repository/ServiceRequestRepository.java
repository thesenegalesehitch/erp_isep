package com.isep.repository;

import com.isep.model.ServiceRequest;
import com.isep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByServiceId(Long serviceId);
    List<ServiceRequest> findByRequester(User requester);
    List<ServiceRequest> findByServiceProviderId(Long providerId);
    List<ServiceRequest> findByStatus(ServiceRequest.RequestStatus status);
}
