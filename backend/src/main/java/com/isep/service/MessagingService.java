package com.isep.service;

import com.isep.model.Conversation;
import com.isep.model.Message;
import com.isep.model.User;
import com.isep.repository.ConversationRepository;
import com.isep.repository.MessageRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessagingService {
    
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public Message sendMessage(Long senderId, Long receiverId, String content, Message.MessageType type) {
        User sender = userRepository.findById(senderId)
            .orElseThrow(() -> new RuntimeException("Sender not found"));
        
        User receiver = userRepository.findById(receiverId)
            .orElseThrow(() -> new RuntimeException("Receiver not found"));
        
        // Find or create conversation
        Conversation conversation = findOrCreateConversation(sender, receiver);
        
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setConversation(conversation);
        message.setContent(content);
        message.setType(type);
        message.setIsRead(false);
        
        message = messageRepository.save(message);
        
        // Update conversation last message time
        conversation.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(conversation);
        
        return message;
    }
    
    @Transactional
    public Message sendGroupMessage(Long senderId, Long conversationId, String content, Message.MessageType type) {
        User sender = userRepository.findById(senderId)
            .orElseThrow(() -> new RuntimeException("Sender not found"));
        
        Conversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new RuntimeException("Conversation not found"));
        
        // Check if user is participant
        if (!conversation.getParticipants().contains(sender)) {
            throw new RuntimeException("User not part of conversation");
        }
        
        Message message = new Message();
        message.setSender(sender);
        message.setConversation(conversation);
        message.setContent(content);
        message.setType(type);
        message.setIsRead(false);
        
        message = messageRepository.save(message);
        
        conversation.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(conversation);
        
        return message;
    }
    
    @Transactional
    public Conversation findOrCreateConversation(User user1, User user2) {
        // Try to find existing private conversation
        List<Conversation> conversations = conversationRepository.findAll();
        
        for (Conversation conv : conversations) {
            if (conv.getType() == Conversation.ConversationType.PRIVATE &&
                conv.getParticipants().contains(user1) &&
                conv.getParticipants().contains(user2) &&
                conv.getParticipants().size() == 2) {
                return conv;
            }
        }
        
        // Create new conversation
        Conversation conversation = new Conversation();
        conversation.setName(user1.getFirstName() + " - " + user2.getFirstName());
        conversation.setType(Conversation.ConversationType.PRIVATE);
        conversation.setParticipants(Set.of(user1, user2));
        
        return conversationRepository.save(conversation);
    }
    
    public List<Message> getConversationMessages(Long conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
    
    public List<Conversation> getUserConversations(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return conversationRepository.findAll().stream()
            .filter(conv -> conv.getParticipants().contains(user))
            .toList();
    }
    
    @Transactional
    public void markAsRead(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message not found"));
        
        if (message.getReceiver() != null && message.getReceiver().getId().equals(userId)) {
            message.setIsRead(true);
            message.setReadAt(LocalDateTime.now());
            messageRepository.save(message);
        }
    }
}

