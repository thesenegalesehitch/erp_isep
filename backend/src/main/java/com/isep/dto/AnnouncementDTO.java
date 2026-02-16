package com.isep.dto;

import com.isep.model.Announcement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {
    private Long id;
    private String title;
    private String content;
    private Announcement.AnnouncementType type;
    private Announcement.Priority priority;
    private Long authorId;
    private String authorName;
    private Set<String> attachments;
    private LocalDateTime expiresAt;
    private Boolean isPublished;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AnnouncementDTO fromEntity(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setType(announcement.getType());
        dto.setPriority(announcement.getPriority());
        dto.setAttachments(announcement.getAttachments());
        dto.setExpiresAt(announcement.getExpiresAt());
        dto.setIsPublished(announcement.getIsPublished());
        dto.setPublishedAt(announcement.getPublishedAt());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setUpdatedAt(announcement.getUpdatedAt());
        
        if (announcement.getAuthor() != null) {
            dto.setAuthorId(announcement.getAuthor().getId());
            dto.setAuthorName(announcement.getAuthor().getFullName());
        }
        
        return dto;
    }
}
