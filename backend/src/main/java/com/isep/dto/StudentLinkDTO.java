package com.isep.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentLinkDTO {
    private Long id;
    private Long parentId;
    private Long studentId;
    private String studentName;
    private String relationship;
    private Boolean isVerified;
    private String verificationCode;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
