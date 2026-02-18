package com.isep.service;

import com.isep.model.Parent;
import com.isep.model.StudentLink;
import com.isep.model.User;
import com.isep.repository.ParentRepository;
import com.isep.repository.StudentLinkRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ParentService {
    
    private final ParentRepository parentRepository;
    private final StudentLinkRepository studentLinkRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;
    
    public Parent registerParent(Parent parent) {
        if (parentRepository.existsByEmail(parent.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        parent.setPassword(passwordEncoder.encode(parent.getPassword()));
        parent.setEmailVerificationToken(UUID.randomUUID().toString());
        parent.setSubscriptionStart(LocalDateTime.now());
        
        Parent savedParent = parentRepository.save(parent);
        
        notificationService.sendVerificationEmail(savedParent);
        
        log.info("Parent registered successfully: {}", savedParent.getEmail());
        return savedParent;
    }
    
    public Parent authenticateParent(String email, String password) {
        Parent parent = parentRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        if (!passwordEncoder.matches(password, parent.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        if (!parent.isActive()) {
            throw new RuntimeException("Account is deactivated");
        }
        
        return parent;
    }
    
    public StudentLink linkStudent(Long parentId, Long studentId, String relationship) {
        Parent parent = parentRepository.findById(parentId)
            .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        User student = userRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (studentLinkRepository.findByParentIdAndStudentId(parentId, studentId).isPresent()) {
            throw new RuntimeException("Student already linked");
        }
        
        if (!parent.canLinkMoreStudents()) {
            throw new RuntimeException("Subscription limit reached");
        }
        
        StudentLink link = StudentLink.builder()
            .parent(parent)
            .student(student)
            .relationship(relationship)
            .verificationCode(generateVerificationCode())
            .build();
        
        StudentLink savedLink = studentLinkRepository.save(link);
        
        notificationService.sendStudentLinkVerification(savedLink);
        
        log.info("Student link created: parent {} -> student {}", parentId, studentId);
        return savedLink;
    }
    
    public StudentLink verifyStudentLink(String verificationCode) {
        StudentLink link = studentLinkRepository.findByVerificationCode(verificationCode)
            .orElseThrow(() -> new RuntimeException("Invalid verification code"));
        
        if (link.isVerified()) {
            throw new RuntimeException("Link already verified");
        }
        
        link.setVerified(true);
        link.setVerificationCode(null);
        
        StudentLink savedLink = studentLinkRepository.save(link);
        
        notificationService.sendLinkConfirmationNotification(savedLink);
        
        log.info("Student link verified: {}", savedLink.getId());
        return savedLink;
    }
    
    public List<StudentLink> getParentStudentLinks(Long parentId) {
        return studentLinkRepository.findVerifiedLinksByParent(parentId);
    }
    
    public Parent upgradeToPremium(Long parentId, String paymentMethod) {
        Parent parent = parentRepository.findById(parentId)
            .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        parent.setSubscriptionType(Parent.SubscriptionType.PREMIUM);
        parent.setSubscriptionStart(LocalDateTime.now());
        parent.setSubscriptionEnd(LocalDateTime.now().plusMonths(1));
        parent.setPaymentMethod(paymentMethod);
        
        Parent savedParent = parentRepository.save(parent);
        
        notificationService.sendSubscriptionConfirmation(savedParent);
        
        log.info("Parent upgraded to premium: {}", parentId);
        return savedParent;
    }
    
    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
    
    @Transactional(readOnly = true)
    public List<Parent> getActiveSubscribers() {
        return parentRepository.findActiveSubscribers();
    }
    
    @Transactional(readOnly = true)
    public long getActivePremiumSubscribersCount() {
        return parentRepository.countActivePremiumSubscribers();
    }
}
