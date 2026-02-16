package com.isep.websocket;

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
    public void sendMessage(@Payload Message message) {
        // Process message and save
        Message savedMessage = messagingService.sendGroupMessage(
            message.getSender().getId(),
            message.getConversation().getId(),
            message.getContent(),
            message.getType()
        );
        
        // Broadcast to conversation participants
        messagingTemplate.convertAndSend(
            "/topic/conversation/" + savedMessage.getConversation().getId(),
            savedMessage
        );
    }
    
    @MessageMapping("/chat.join")
    public void joinConversation(@Payload Long conversationId) {
        // User joins conversation
        // Could implement typing indicators, presence, etc.
    }
}

