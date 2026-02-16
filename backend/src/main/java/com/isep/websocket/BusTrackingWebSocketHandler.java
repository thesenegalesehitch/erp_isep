package com.isep.websocket;

import com.isep.model.Bus;
import com.isep.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BusTrackingWebSocketHandler {
    
    private final BusRepository busRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    @Scheduled(fixedRate = 5000) // Update every 5 seconds
    public void broadcastBusUpdates() {
        List<Bus> buses = busRepository.findAll();
        
        for (Bus bus : buses) {
            if (bus.getStatus() == Bus.BusStatus.IN_TRANSIT) {
                messagingTemplate.convertAndSend("/topic/bus/" + bus.getId(), bus);
            }
        }
        
        // Broadcast all buses to general topic
        messagingTemplate.convertAndSend("/topic/buses", buses);
    }
}

