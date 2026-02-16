package com.isep.dto;

import com.isep.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;
    private String title;
    private String description;
    private Activity.ActivityType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private Integer maxParticipants;
    private Integer currentParticipants;
    private Long organizerId;
    private String organizerName;
    private Boolean isActive;
    private Boolean hasAvailableSpots;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ActivityDTO fromEntity(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setTitle(activity.getTitle());
        dto.setDescription(activity.getDescription());
        dto.setType(activity.getType());
        dto.setStartDate(activity.getStartDate());
        dto.setEndDate(activity.getEndDate());
        dto.setLocation(activity.getLocation());
        dto.setMaxParticipants(activity.getMaxParticipants());
        dto.setCurrentParticipants(activity.getCurrentParticipants());
        dto.setIsActive(activity.getIsActive());
        dto.setHasAvailableSpots(activity.hasAvailableSpots());
        dto.setCreatedAt(activity.getCreatedAt());
        dto.setUpdatedAt(activity.getUpdatedAt());
        
        if (activity.getOrganizer() != null) {
            dto.setOrganizerId(activity.getOrganizer().getId());
            dto.setOrganizerName(activity.getOrganizer().getFullName());
        }
        
        return dto;
    }
}
