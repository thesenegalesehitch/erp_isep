package com.isep.dto;

import com.isep.model.ServiceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDTO {
    private Long id;
    private Long serviceId;
    private String serviceTitle;
    private Long requesterId;
    private String requesterName;
    private String message;
    private LocalDateTime requestedDate;
    private ServiceRequest.RequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ServiceRequestDTO fromEntity(ServiceRequest request) {
        ServiceRequestDTO dto = new ServiceRequestDTO();
        dto.setId(request.getId());
        dto.setMessage(request.getMessage());
        dto.setRequestedDate(request.getRequestedDate());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        
        if (request.getService() != null) {
            dto.setServiceId(request.getService().getId());
            dto.setServiceTitle(request.getService().getTitle());
        }
        if (request.getRequester() != null) {
            dto.setRequesterId(request.getRequester().getId());
            dto.setRequesterName(request.getRequester().getFullName());
        }
        
        return dto;
    }
}
