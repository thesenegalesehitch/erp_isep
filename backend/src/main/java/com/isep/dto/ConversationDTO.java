package com.isep.dto;

import com.isep.model.Conversation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDTO {
    private Long id;
    private String name;
    private Conversation.ConversationType type;
    private Set<Long> participantIds;
    private Set<String> participantNames;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer unreadCount;

    public static ConversationDTO fromEntity(Conversation conversation) {
        ConversationDTO dto = new ConversationDTO();
        dto.setId(conversation.getId());
        dto.setName(conversation.getName());
        dto.setType(conversation.getType());
        dto.setCreatedAt(conversation.getCreatedAt());
        dto.setUpdatedAt(conversation.getUpdatedAt());
        dto.setLastMessageAt(conversation.getLastMessageAt());
        
        if (conversation.getParticipants() != null) {
            dto.setParticipantIds(
                conversation.getParticipants().stream()
                    .map(user -> user.getId())
                    .collect(Collectors.toSet())
            );
            dto.setParticipantNames(
                conversation.getParticipants().stream()
                    .map(user -> user.getFullName())
                    .collect(Collectors.toSet())
            );
        }
        
        return dto;
    }
}
