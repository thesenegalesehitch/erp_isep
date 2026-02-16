package com.isep.controller;

import com.isep.model.Announcement;
import com.isep.model.User;
import com.isep.repository.AnnouncementRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {
    
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements(
            @RequestParam(required = false) Boolean published) {
        List<Announcement> announcements;
        if (published != null && published) {
            announcements = announcementRepository.findByIsPublishedTrueAndExpiresAtAfter(
                LocalDateTime.now());
        } else {
            announcements = announcementRepository.findAll();
        }
        return ResponseEntity.ok(announcements);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable Long id) {
        return announcementRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(
            @Valid @RequestBody Announcement announcement,
            Authentication authentication) {
        User author = userRepository.findByEmail(authentication.getName())
            .orElseThrow();
        announcement.setAuthor(author);
        announcement.setIsPublished(false);
        return ResponseEntity.ok(announcementRepository.save(announcement));
    }
    
    @PutMapping("/{id}/publish")
    public ResponseEntity<Announcement> publishAnnouncement(
            @PathVariable Long id,
            Authentication authentication) {
        return announcementRepository.findById(id)
            .map(announcement -> {
                User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow();
                // Only admin can publish
                if (!user.getRole().equals(User.Role.ADMIN)) {
                    return ResponseEntity.<Announcement>forbidden().build();
                }
                announcement.setIsPublished(true);
                announcement.setPublishedAt(LocalDateTime.now());
                return ResponseEntity.ok(announcementRepository.save(announcement));
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

