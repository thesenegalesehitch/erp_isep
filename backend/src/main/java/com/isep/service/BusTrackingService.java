package com.isep.service;

import com.isep.model.Bus;
import com.isep.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BusTrackingService {
    
    private final BusRepository busRepository;
    private final Random random = new Random();
    
    // Simuler la mise à jour de position toutes les 10 secondes
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void updateBusPositions() {
        List<Bus> buses = busRepository.findAll();
        
        for (Bus bus : buses) {
            if (bus.getStatus() == Bus.BusStatus.IN_TRANSIT) {
                // Simuler mouvement du bus (exemple pour Dakar)
                double lat = 14.7167 + (random.nextDouble() - 0.5) * 0.01;
                double lng = -17.4677 + (random.nextDouble() - 0.5) * 0.01;
                
                bus.setCurrentLocationLat(lat);
                bus.setCurrentLocationLng(lng);
                busRepository.save(bus);
            }
        }
    }
    
    @Transactional
    public void updateBusStatus(Long busId, Bus.BusStatus status) {
        Bus bus = busRepository.findById(busId)
            .orElseThrow(() -> new RuntimeException("Bus not found"));
        
        bus.setStatus(status);
        busRepository.save(bus);
    }
    
    @Transactional
    public void updateBusLocation(Long busId, Double lat, Double lng) {
        Bus bus = busRepository.findById(busId)
            .orElseThrow(() -> new RuntimeException("Bus not found"));
        
        bus.setCurrentLocationLat(lat);
        bus.setCurrentLocationLng(lng);
        busRepository.save(bus);
    }
    
    public List<Bus> getAllActiveBuses() {
        return busRepository.findByStatus(Bus.BusStatus.IN_TRANSIT);
    }
    
    public Bus getBusById(Long busId) {
        return busRepository.findById(busId)
            .orElseThrow(() -> new RuntimeException("Bus not found"));
    }
}

