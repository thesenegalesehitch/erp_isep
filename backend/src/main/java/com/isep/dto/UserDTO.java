package com.isep.dto;

import com.isep.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String studentNumber;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private User.Role role;
    private String specialty;
    private String profilePhoto;
    private String location;
    private Boolean isActive;
    private Boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setStudentNumber(user.getStudentNumber());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setSpecialty(user.getSpecialty());
        dto.setProfilePhoto(user.getProfilePhoto());
        dto.setLocation(user.getLocation());
        dto.setIsActive(user.getIsActive());
        dto.setEmailVerified(user.getEmailVerified());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        
        return dto;
    }
}
