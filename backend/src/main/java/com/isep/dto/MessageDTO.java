package com.isep.dto;

import com.isep.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long conversationId;
    private String content;
    private Message.MessageType type;
    private String fileUrl;
    private Boolean isRead;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;

    public static MessageDTO fromEntity(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setType(message.getType());
        dto.setFileUrl(message.getFileUrl());
        dto.setIsRead(message.getIsRead());
        dto.setReadAt(message.getReadAt());
        dto.setCreatedAt(message.getCreatedAt());
        
        if (message.getSender() != null) {
            dto.setSenderId(message.getSender().getId());
            dto.setSenderName(message.getSender().getFullName());
        }
        if (message.getReceiver() != null) {
            dto.setReceiverId(message.getReceiver().getId());
            dto.setReceiverName(message.getReceiver().getFullName());
        }
        if (message.getConversation() != null) {
            dto.setConversationId(message.getConversation().getId());
        }
        
        return dto;
    }
}
