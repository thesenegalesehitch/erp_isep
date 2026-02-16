package com.isep.controller;

import com.isep.model.Message;
import com.isep.model.User;
import com.isep.repository.MessageRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<Message>> getConversationMessages(
            @PathVariable Long conversationId) {
        List<Message> messages = messageRepository.findByConversationId(conversationId);
        return ResponseEntity.ok(messages);
    }
    
    @PostMapping
    public ResponseEntity<Message> sendMessage(
            @Valid @RequestBody Message message,
            Authentication authentication) {
        User sender = userRepository.findByEmail(authentication.getName())
            .orElseThrow();
        message.setSender(sender);
        return ResponseEntity.ok(messageRepository.save(message));
    }
    
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        return messageRepository.save(message);
    }
}

