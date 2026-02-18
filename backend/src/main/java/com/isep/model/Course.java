package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String courseCode;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private String department;
    
    @Column(nullable = false)
    private String program;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseType courseType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyLevel studyLevel;
    
    @Column(nullable = false)
    private Integer credits;
    
    @Column(nullable = false)
    private Integer totalHours;
    
    private Integer theoreticalHours;
    
    private Integer practicalHours;
    
    private Integer labHours;
    
    @Column(nullable = false)
    private String semester;
    
    @Column(nullable = false)
    private String academicYear;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Column(nullable = false)
    private Integer maxStudents;
    
    private Integer currentEnrolled = 0;
    
    private String prerequisites;
    
    private String objectives;
    
    private String evaluationMethod;
    
    private String bibliography;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CourseSchedule> schedules;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    public enum CourseType {
        MANDATORY,
        OPTIONAL,
        ELECTIVE,
        WORKSHOP,
        SEMINAR,
        INTERNSHIP
    }
    
    public boolean isFull() {
        return currentEnrolled >= maxStudents;
    }
    
    public boolean hasAvailableSlots() {
        return currentEnrolled < maxStudents;
    }
    
    public int getAvailableSlots() {
        return maxStudents - currentEnrolled;
    }
}
