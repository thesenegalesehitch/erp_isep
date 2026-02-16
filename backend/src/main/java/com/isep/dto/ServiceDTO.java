package com.isep.dto;

import com.isep.model.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private Long id;
    private String title;
    private String description;
    private Service.ServiceCategory category;
    private BigDecimal price;
    private String location;
    private Boolean isAvailable;
    private Double averageRating;
    private Integer totalRatings;
    private Long providerId;
    private String providerName;
    private Set<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ServiceDTO fromEntity(Service service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setId(service.getId());
        dto.setTitle(service.getTitle());
        dto.setDescription(service.getDescription());
        dto.setCategory(service.getCategory());
        dto.setPrice(service.getPrice());
        dto.setLocation(service.getLocation());
        dto.setIsAvailable(service.getIsAvailable());
        dto.setAverageRating(service.getAverageRating());
        dto.setTotalRatings(service.getTotalRatings());
        dto.setImages(service.getImages());
        dto.setCreatedAt(service.getCreatedAt());
        dto.setUpdatedAt(service.getUpdatedAt());
        
        if (service.getProvider() != null) {
            dto.setProviderId(service.getProvider().getId());
            dto.setProviderName(service.getProvider().getFullName());
        }
        
        return dto;
    }
}
