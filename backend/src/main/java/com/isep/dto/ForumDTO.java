package com.isep.dto;

import com.isep.model.Forum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumDTO {
    private Long id;
    private String name;
    private String description;
    private String specialty;
    private Long moderatorId;
    private String moderatorName;
    private Integer postCount;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ForumDTO fromEntity(Forum forum) {
        ForumDTO dto = new ForumDTO();
        dto.setId(forum.getId());
        dto.setName(forum.getName());
        dto.setDescription(forum.getDescription());
        dto.setSpecialty(forum.getSpecialty());
        dto.setIsActive(forum.getIsActive());
        dto.setCreatedAt(forum.getCreatedAt());
        dto.setUpdatedAt(forum.getUpdatedAt());
        
        if (forum.getModerator() != null) {
            dto.setModeratorId(forum.getModerator().getId());
            dto.setModeratorName(forum.getModerator().getFullName());
        }
        
        if (forum.getPosts() != null) {
            dto.setPostCount(forum.getPosts().size());
        }
        
        return dto;
    }
}
