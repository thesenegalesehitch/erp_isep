package com.isep.controller;

import com.isep.dto.MessageDTO;
import com.isep.model.Message;
import com.isep.model.User;
import com.isep.repository.UserRepository;
import com.isep.service.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    
    private final MessagingService messagingService;
    private final UserRepository userRepository;
    
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<MessageDTO>> getConversationMessages(
            @PathVariable Long conversationId) {
        List<Message> messages = messagingService.getConversationMessages(conversationId);
        List<MessageDTO> messageDTOs = messages.stream()
            .map(MessageDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(messageDTOs);
    }
    
    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(
            @Valid @RequestBody MessageDTO messageDto,
            Authentication authentication) {
        User sender = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Message message;
        if (messageDto.getConversationId() != null) {
            message = messagingService.sendGroupMessage(
                sender.getId(),
                messageDto.getConversationId(),
                messageDto.getContent(),
                messageDto.getType()
            );
        } else if (messageDto.getReceiverId() != null) {
            message = messagingService.sendMessage(
                sender.getId(),
                messageDto.getReceiverId(),
                messageDto.getContent(),
                messageDto.getType()
            );
        } else {
            throw new IllegalArgumentException("Either conversationId or receiverId must be provided");
        }
        
        return ResponseEntity.ok(MessageDTO.fromEntity(message));
    }
}

