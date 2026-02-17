package com.isep.websocket;

import com.isep.dto.BusDTO;
import com.isep.model.Bus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BusTrackingWebSocketHandler {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    public void broadcastBusUpdate(Bus bus) {
        if (bus.getStatus() == Bus.BusStatus.IN_TRANSIT) {
            messagingTemplate.convertAndSend("/topic/bus/" + bus.getId(), BusDTO.fromEntity(bus));
        }
    }

    public void broadcastAllBuses(List<Bus> buses) {
        List<BusDTO> busDTOs = buses.stream()
            .map(BusDTO::fromEntity)
            .collect(Collectors.toList());
        messagingTemplate.convertAndSend("/topic/buses", busDTOs);
    }
}

