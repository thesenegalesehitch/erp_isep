package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Grade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String evaluationTitle;
    
    @Column(nullable = false)
    private BigDecimal score;
    
    @Column(nullable = false)
    private BigDecimal maxScore;
    
    @Column(nullable = false)
    private BigDecimal weight;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GradeType gradeType;
    
    @Column(nullable = false)
    private String semester;
    
    @Column(nullable = false)
    private String academicYear;
    
    private String comments;
    
    private String gradingScale;
    
    private LocalDateTime gradedDate;
    
    private LocalDateTime publishedDate;
    
    @Column(nullable = false)
    private Boolean isPublished = false;
    
    @Column(nullable = false)
    private Boolean isValidated = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public enum GradeType {
        EXAM,
        QUIZ,
        ASSIGNMENT,
        PROJECT,
        PRESENTATION,
        LAB_REPORT,
        PARTICIPATION,
        HOMEWORK,
        MIDTERM,
        FINAL,
        THESIS,
        INTERNSHIP_EVALUATION
    }
    
    public BigDecimal getPercentage() {
        if (maxScore.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return score.divide(maxScore, 2, BigDecimal.ROUND_HALF_UP)
                   .multiply(new BigDecimal("100"));
    }
    
    public boolean isPassingGrade(BigDecimal passingPercentage) {
        return getPercentage().compareTo(passingPercentage) >= 0;
    }
    
    public BigDecimal getWeightedScore() {
        return getPercentage().multiply(weight)
                             .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
    }
}
