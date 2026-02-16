package com.isep.dto;

import com.isep.model.ForumPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumPostDTO {
    private Long id;
    private Long forumId;
    private String forumName;
    private Long authorId;
    private String authorName;
    private String title;
    private String content;
    private Long parentPostId;
    private Integer replyCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ForumPostDTO fromEntity(ForumPost post) {
        ForumPostDTO dto = new ForumPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        
        if (post.getForum() != null) {
            dto.setForumId(post.getForum().getId());
            dto.setForumName(post.getForum().getName());
        }
        if (post.getAuthor() != null) {
            dto.setAuthorId(post.getAuthor().getId());
            dto.setAuthorName(post.getAuthor().getFullName());
        }
        if (post.getParentPost() != null) {
            dto.setParentPostId(post.getParentPost().getId());
        }
        
        return dto;
    }
}
