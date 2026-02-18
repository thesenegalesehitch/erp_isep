package com.isep.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StudentGradeDTO {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long schoolId;
    private String schoolName;
    private String subject;
    private String examType;
    private BigDecimal grade;
    private BigDecimal maxGrade;
    private BigDecimal coefficient;
    private String semester;
    private String academicYear;
    private String comments;
    private LocalDateTime gradeDate;
    private BigDecimal percentage;
    private Boolean isPassing;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
