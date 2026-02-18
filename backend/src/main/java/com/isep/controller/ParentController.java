package com.isep.controller;

import com.isep.model.Parent;
import com.isep.model.StudentLink;
import com.isep.service.ParentService;
import com.isep.dto.ParentDTO;
import com.isep.dto.StudentLinkDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/parents")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ParentController {
    
    private final ParentService parentService;
    
    @PostMapping("/register")
    public ResponseEntity<ParentDTO> registerParent(@Valid @RequestBody ParentDTO parentDTO) {
        Parent parent = convertToEntity(parentDTO);
        Parent savedParent = parentService.registerParent(parent);
        return ResponseEntity.ok(convertToDTO(savedParent));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ParentDTO> loginParent(@RequestBody LoginRequest loginRequest) {
        Parent parent = parentService.authenticateParent(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(convertToDTO(parent));
    }
    
    @PostMapping("/{parentId}/students/{studentId}/link")
    public ResponseEntity<StudentLinkDTO> linkStudent(
            @PathVariable Long parentId,
            @PathVariable Long studentId,
            @RequestBody LinkRequest request) {
        StudentLink link = parentService.linkStudent(parentId, studentId, request.getRelationship());
        return ResponseEntity.ok(convertToDTO(link));
    }
    
    @PostMapping("/verify-link/{verificationCode}")
    public ResponseEntity<StudentLinkDTO> verifyStudentLink(@PathVariable String verificationCode) {
        StudentLink link = parentService.verifyStudentLink(verificationCode);
        return ResponseEntity.ok(convertToDTO(link));
    }
    
    @GetMapping("/{parentId}/students")
    public ResponseEntity<List<StudentLinkDTO>> getParentStudents(@PathVariable Long parentId) {
        List<StudentLink> links = parentService.getParentStudentLinks(parentId);
        return ResponseEntity.ok(links.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    @PostMapping("/{parentId}/upgrade-premium")
    public ResponseEntity<ParentDTO> upgradeToPremium(
            @PathVariable Long parentId,
            @RequestBody UpgradeRequest request) {
        Parent parent = parentService.upgradeToPremium(parentId, request.getPaymentMethod());
        return ResponseEntity.ok(convertToDTO(parent));
    }
    
    @GetMapping("/active-subscribers")
    public ResponseEntity<List<ParentDTO>> getActiveSubscribers() {
        List<Parent> parents = parentService.getActiveSubscribers();
        return ResponseEntity.ok(parents.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    @GetMapping("/premium-count")
    public ResponseEntity<Long> getPremiumSubscribersCount() {
        long count = parentService.getActivePremiumSubscribersCount();
        return ResponseEntity.ok(count);
    }
    
    private Parent convertToEntity(ParentDTO dto) {
        Parent parent = new Parent();
        parent.setEmail(dto.getEmail());
        parent.setPassword(dto.getPassword());
        parent.setFirstName(dto.getFirstName());
        parent.setLastName(dto.getLastName());
        parent.setPhone(dto.getPhone());
        return parent;
    }
    
    private ParentDTO convertToDTO(Parent parent) {
        ParentDTO dto = new ParentDTO();
        dto.setId(parent.getId());
        dto.setEmail(parent.getEmail());
        dto.setFirstName(parent.getFirstName());
        dto.setLastName(parent.getLastName());
        dto.setPhone(parent.getPhone());
        dto.setSubscriptionType(parent.getSubscriptionType());
        dto.setSubscriptionStart(parent.getSubscriptionStart());
        dto.setSubscriptionEnd(parent.getSubscriptionEnd());
        dto.setIsActive(parent.getIsActive());
        return dto;
    }
    
    private StudentLinkDTO convertToDTO(StudentLink link) {
        StudentLinkDTO dto = new StudentLinkDTO();
        dto.setId(link.getId());
        dto.setParentId(link.getParent().getId());
        dto.setStudentId(link.getStudent().getId());
        dto.setStudentName(link.getStudent().getFullName());
        dto.setRelationship(link.getRelationship());
        dto.setIsVerified(link.getVerified());
        dto.setIsActive(link.getIsActive());
        return dto;
    }
    
    public static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class LinkRequest {
        private String relationship;
        
        public String getRelationship() { return relationship; }
        public void setRelationship(String relationship) { this.relationship = relationship; }
    }
    
    public static class UpgradeRequest {
        private String paymentMethod;
        
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    }
}
