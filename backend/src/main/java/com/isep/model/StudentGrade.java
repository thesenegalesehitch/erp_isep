package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_grades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StudentGrade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @Column(name = "subject", nullable = false)
    private String subject;
    
    @Column(name = "exam_type", nullable = false)
    private String examType;
    
    @Column(name = "grade", nullable = false, precision = 5, scale = 2)
    private BigDecimal grade;
    
    @Column(name = "max_grade", nullable = false, precision = 5, scale = 2)
    private BigDecimal maxGrade;
    
    @Column(name = "coefficient", precision = 3, scale = 1)
    private BigDecimal coefficient = BigDecimal.ONE;
    
    @Column(name = "semester", nullable = false)
    private String semester;
    
    @Column(name = "academic_year", nullable = false)
    private String academicYear;
    
    @Column(name = "comments")
    private String comments;
    
    @Column(name = "grade_date")
    private LocalDateTime gradeDate;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public BigDecimal getPercentage() {
        if (maxGrade.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return grade.multiply(BigDecimal.valueOf(100)).divide(maxGrade, 2, BigDecimal.ROUND_HALF_UP);
    }
    
    public boolean isPassing() {
        return getPercentage().compareTo(BigDecimal.valueOf(50)) >= 0;
    }
}
