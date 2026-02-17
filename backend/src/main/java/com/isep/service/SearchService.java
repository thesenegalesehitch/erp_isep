package com.isep.service;

import com.isep.dto.SearchResultDTO;
import com.isep.repository.ActivityRepository;
import com.isep.repository.AnnouncementRepository;
import com.isep.repository.ForumRepository;
import com.isep.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    
    private final ServiceRepository serviceRepository;
    private final ForumRepository forumRepository;
    private final ActivityRepository activityRepository;
    private final AnnouncementRepository announcementRepository;
    
    public List<SearchResultDTO> search(String query) {
        List<SearchResultDTO> results = new ArrayList<>();
        
        // Search Services
        results.addAll(serviceRepository.searchByQuery(query).stream()
            .map(s -> new SearchResultDTO(
                "SERVICE", 
                s.getId(), 
                s.getTitle(), 
                s.getDescription(), 
                "/services/" + s.getId()))
            .collect(Collectors.toList()));
            
        // Search Forums
        results.addAll(forumRepository.searchByQuery(query).stream()
            .map(f -> new SearchResultDTO(
                "FORUM", 
                f.getId(), 
                f.getTitle(), 
                f.getDescription(), 
                "/forums/" + f.getId()))
            .collect(Collectors.toList()));
            
        // Search Activities
        results.addAll(activityRepository.searchByQuery(query).stream()
            .map(a -> new SearchResultDTO(
                "ACTIVITY", 
                a.getId(), 
                a.getTitle(), 
                a.getDescription(), 
                "/activities/" + a.getId()))
            .collect(Collectors.toList()));
            
        // Search Announcements
        results.addAll(announcementRepository.searchByQuery(query).stream()
            .map(a -> new SearchResultDTO(
                "ANNOUNCEMENT", 
                a.getId(), 
                a.getTitle(), 
                a.getContent(), 
                "/announcements/" + a.getId()))
            .collect(Collectors.toList()));
            
        return results;
    }
}
