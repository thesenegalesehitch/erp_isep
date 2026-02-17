package com.isep.service;

import com.isep.dto.NotificationDTO;
import com.isep.model.Notification;
import com.isep.model.User;
import com.isep.repository.NotificationRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    @Transactional
    public void sendNotification(Long userId, String title, String message, String type, Long relatedId, String relatedType) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        Notification notification = new Notification();
        notification.setRecipient(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setRelatedEntityId(relatedId);
        notification.setRelatedEntityType(relatedType);
        
        notification = notificationRepository.save(notification);
        
        // Broadcast via WebSocket
        // Using a topic that the client can subscribe to directly
        messagingTemplate.convertAndSend(
            "/topic/user/" + userId + "/notifications",
            NotificationDTO.fromEntity(notification)
        );
    }
    
    public void sendNotification(Long userId, String title, String message, String type) {
        sendNotification(userId, title, message, type, null, null);
    }
    
    public void notifyActivityReminder(Long userId, Long activityId, String activityTitle) {
        sendNotification(
            userId,
            "Rappel d'activité",
            "L'activité '" + activityTitle + "' commence bientôt",
            "ACTIVITY_REMINDER",
            activityId,
            "ACTIVITY"
        );
    }
    
    public void notifyNewMessage(Long userId, Long senderId, String senderName) {
        sendNotification(
            userId,
            "Nouveau message",
            "Vous avez reçu un message de " + senderName,
            "NEW_MESSAGE",
            senderId,
            "USER" // or CONVERSATION
        );
    }
    
    public void notifyBusArrival(Long userId, Long busId, String lineNumber) {
        sendNotification(
            userId,
            "Bus en approche",
            "Le bus ligne " + lineNumber + " arrive dans 5 minutes",
            "BUS_ARRIVAL",
            busId,
            "BUS"
        );
    }
    
    public void notifyServiceRequest(Long userId, Long serviceId, String serviceTitle) {
        sendNotification(
            userId,
            "Nouvelle demande",
            "Nouvelle demande pour votre service: " + serviceTitle,
            "SERVICE_REQUEST",
            serviceId,
            "SERVICE"
        );
    }
    
    public void notifyAnnouncement(Long userId, String announcementTitle) {
        sendNotification(
            userId,
            "Nouvelle annonce",
            announcementTitle,
            "ANNOUNCEMENT",
            null,
            "ANNOUNCEMENT"
        );
    }
    
    public List<Notification> getUserNotifications(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByRecipientOrderByCreatedAtDesc(user);
    }
    
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
    
    @Transactional
    public void markAllAsRead(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<Notification> notifications = notificationRepository.findByRecipientAndIsReadFalseOrderByCreatedAtDesc(user);
        for (Notification notification : notifications) {
            notification.setIsRead(true);
        }
        notificationRepository.saveAll(notifications);
    }
}

