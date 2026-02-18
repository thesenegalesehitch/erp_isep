package com.isep.service;

import com.isep.dto.SchoolDTO;
import com.isep.model.School;
import com.isep.repository.SchoolRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {
    
    private final SchoolRepository schoolRepository;
    private final UserRepository userRepository;
    
    public Page<SchoolDTO> getAllSchools(Pageable pageable) {
        return schoolRepository.findActiveSchools(pageable)
                .map(SchoolDTO::fromEntity);
    }
    
    public SchoolDTO getSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
        return SchoolDTO.fromEntity(school);
    }
    
    public SchoolDTO createSchool(SchoolDTO schoolDTO) {
        if (schoolRepository.existsByCode(schoolDTO.getCode())) {
            throw new RuntimeException("School code already exists");
        }
        if (schoolRepository.existsByEmail(schoolDTO.getEmail())) {
            throw new RuntimeException("School email already exists");
        }
        
        School school = School.builder()
                .name(schoolDTO.getName())
                .code(schoolDTO.getCode())
                .email(schoolDTO.getEmail())
                .phone(schoolDTO.getPhone())
                .address(schoolDTO.getAddress())
                .city(schoolDTO.getCity())
                .country(schoolDTO.getCountry())
                .subscriptionPlan(School.SubscriptionPlan.BASIC)
                .subscriptionStart(LocalDateTime.now())
                .maxStudents(schoolDTO.getMaxStudents() != null ? schoolDTO.getMaxStudents() : 200)
                .currentStudentCount(0)
                .isActive(true)
                .build();
        
        school = schoolRepository.save(school);
        return SchoolDTO.fromEntity(school);
    }
    
    public SchoolDTO updateSchool(Long id, SchoolDTO schoolDTO) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
        
        if (!school.getCode().equals(schoolDTO.getCode()) && schoolRepository.existsByCode(schoolDTO.getCode())) {
            throw new RuntimeException("School code already exists");
        }
        
        school.setName(schoolDTO.getName());
        school.setCode(schoolDTO.getCode());
        school.setEmail(schoolDTO.getEmail());
        school.setPhone(schoolDTO.getPhone());
        school.setAddress(schoolDTO.getAddress());
        school.setCity(schoolDTO.getCity());
        school.setCountry(schoolDTO.getCountry());
        
        school = schoolRepository.save(school);
        return SchoolDTO.fromEntity(school);
    }
    
    public void deleteSchool(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
        
        school.setIsActive(false);
        schoolRepository.save(school);
    }
    
    public Map<String, Object> getSchoolAnalytics(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
        
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("schoolName", school.getName());
        analytics.put("totalStudents", school.getCurrentStudentCount());
        analytics.put("maxStudents", school.getMaxStudents());
        analytics.put("utilizationRate", (double) school.getCurrentStudentCount() / school.getMaxStudents() * 100);
        analytics.put("subscriptionPlan", school.getSubscriptionPlan());
        analytics.put("subscriptionActive", school.isSubscriptionActive());
        analytics.put("subscriptionEnd", school.getSubscriptionEnd());
        
        return analytics;
    }
    
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalSchools = schoolRepository.countActiveSchoolsWithValidSubscription();
        stats.put("totalActiveSchools", totalSchools);
        
        List<School> schoolsAtCapacity = schoolRepository.findSchoolsAtCapacity();
        stats.put("schoolsAtCapacity", schoolsAtCapacity.size());
        
        List<School> expiringSoon = schoolRepository.findSchoolsWithExpiringSubscription(LocalDateTime.now().plusDays(30));
        stats.put("subscriptionsExpiringSoon", expiringSoon.size());
        
        return stats;
    }
    
    public boolean isSchoolMember(Long schoolId, String email) {
        return userRepository.existsByEmailAndSchoolId(email, schoolId);
    }
    
    public boolean isSchoolAdmin(Long schoolId, String email) {
        return userRepository.existsByEmailAndSchoolIdAndRole(email, schoolId, User.Role.ADMIN);
    }
}
