package com.isep.websocket;

import com.isep.dto.MessageDTO;
import com.isep.model.Message;
import com.isep.service.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessagingWebSocketHandler {
    
    private final MessagingService messagingService;
    private final SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDTO messageDto) {
        // Process message and save
        Message savedMessage;
        
        if (messageDto.getConversationId() != null) {
            savedMessage = messagingService.sendGroupMessage(
                messageDto.getSenderId(),
                messageDto.getConversationId(),
                messageDto.getContent(),
                messageDto.getType()
            );
        } else if (messageDto.getReceiverId() != null) {
             savedMessage = messagingService.sendMessage(
                messageDto.getSenderId(),
                messageDto.getReceiverId(),
                messageDto.getContent(),
                messageDto.getType()
            );
        } else {
             // Handle error or ignore
             return;
        }
        
        // Broadcast to conversation participants
        messagingTemplate.convertAndSend(
            "/topic/conversation/" + savedMessage.getConversation().getId(),
            MessageDTO.fromEntity(savedMessage)
        );
    }
    
    @MessageMapping("/chat.join")
    public void joinConversation(@Payload Long conversationId) {
        // User joins conversation
        // Could implement typing indicators, presence, etc.
    }
}

