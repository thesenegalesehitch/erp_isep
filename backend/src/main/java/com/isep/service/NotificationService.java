package com.isep.service;

import com.isep.model.User;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final UserRepository userRepository;
    
    public void sendNotification(Long userId, String title, String message, String type) {
        // TODO: Implémenter avec Firebase Cloud Messaging
        // Pour l'instant, on simule juste la notification
        System.out.println("Notification to user " + userId + ": " + title + " - " + message);
    }
    
    public void notifyActivityReminder(Long userId, Long activityId, String activityTitle) {
        sendNotification(
            userId,
            "Rappel d'activité",
            "L'activité '" + activityTitle + "' commence bientôt",
            "ACTIVITY_REMINDER"
        );
    }
    
    public void notifyNewMessage(Long userId, Long senderId, String senderName) {
        sendNotification(
            userId,
            "Nouveau message",
            "Vous avez reçu un message de " + senderName,
            "NEW_MESSAGE"
        );
    }
    
    public void notifyBusArrival(Long userId, Long busId, String lineNumber) {
        sendNotification(
            userId,
            "Bus en approche",
            "Le bus ligne " + lineNumber + " arrive dans 5 minutes",
            "BUS_ARRIVAL"
        );
    }
    
    public void notifyServiceRequest(Long userId, Long serviceId, String serviceTitle) {
        sendNotification(
            userId,
            "Nouvelle demande",
            "Nouvelle demande pour votre service: " + serviceTitle,
            "SERVICE_REQUEST"
        );
    }
    
    public void notifyAnnouncement(Long userId, String announcementTitle) {
        sendNotification(
            userId,
            "Nouvelle annonce",
            announcementTitle,
            "ANNOUNCEMENT"
        );
    }
}

