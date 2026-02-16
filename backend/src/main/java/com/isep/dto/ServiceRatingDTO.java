package com.isep.dto;

import com.isep.model.ServiceRating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRatingDTO {
    private Long id;
    private Long serviceId;
    private String serviceTitle;
    private Long userId;
    private String userName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public static ServiceRatingDTO fromEntity(ServiceRating rating) {
        ServiceRatingDTO dto = new ServiceRatingDTO();
        dto.setId(rating.getId());
        dto.setRating(rating.getRating());
        dto.setComment(rating.getComment());
        dto.setCreatedAt(rating.getCreatedAt());
        
        if (rating.getService() != null) {
            dto.setServiceId(rating.getService().getId());
            dto.setServiceTitle(rating.getService().getTitle());
        }
        if (rating.getUser() != null) {
            dto.setUserId(rating.getUser().getId());
            dto.setUserName(rating.getUser().getFullName());
        }
        
        return dto;
    }
}
