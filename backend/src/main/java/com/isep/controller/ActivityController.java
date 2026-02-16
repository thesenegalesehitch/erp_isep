package com.isep.controller;

import com.isep.model.Activity;
import com.isep.model.ActivityRegistration;
import com.isep.model.User;
import com.isep.repository.ActivityRepository;
import com.isep.repository.ActivityRegistrationRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    
    private final ActivityRepository activityRepository;
    private final ActivityRegistrationRepository activityRegistrationRepository;
    private final UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivity(@PathVariable Long id) {
        return activityRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Activity> createActivity(
            @Valid @RequestBody Activity activity,
            Authentication authentication) {
        User organizer = userRepository.findByEmail(authentication.getName())
            .orElseThrow();
        activity.setOrganizer(organizer);
        return ResponseEntity.ok(activityRepository.save(activity));
    }
    
    @PostMapping("/{id}/register")
    public ResponseEntity<ActivityRegistration> registerForActivity(
            @PathVariable Long id,
            Authentication authentication) {
        Activity activity = activityRepository.findById(id).orElseThrow();
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow();
        
        if (!activity.hasAvailableSpots()) {
            return ResponseEntity.badRequest().build();
        }
        
        ActivityRegistration registration = new ActivityRegistration();
        registration.setActivity(activity);
        registration.setUser(user);
        registration.setStatus(ActivityRegistration.RegistrationStatus.REGISTERED);
        
        activity.setCurrentParticipants(activity.getCurrentParticipants() + 1);
        activityRepository.save(activity);
        
        return ResponseEntity.ok(activityRegistrationRepository.save(registration));
    }
}

