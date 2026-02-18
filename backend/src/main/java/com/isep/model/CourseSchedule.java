package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_schedules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourseSchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;
    
    @Column(nullable = false)
    private LocalTime startTime;
    
    @Column(nullable = false)
    private LocalTime endTime;
    
    @Column(nullable = false)
    private String roomNumber;
    
    @Column(nullable = false)
    private String building;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleType scheduleType;
    
    @Column(nullable = false)
    private String semester;
    
    @Column(nullable = false)
    private String academicYear;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    private String notes;
    
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
    
    public enum ScheduleType {
        LECTURE,
        TUTORIAL,
        LAB,
        WORKSHOP,
        SEMINAR,
        EXAM_REVIEW,
        EXAM
    }
    
    public int getDurationInMinutes() {
        return (int) java.time.Duration.between(startTime, endTime).toMinutes();
    }
    
    public boolean conflictsWith(CourseSchedule other) {
        if (!dayOfWeek.equals(other.dayOfWeek) || !semester.equals(other.semester) || !academicYear.equals(other.academicYear)) {
            return false;
        }
        
        return (startTime.isBefore(other.endTime) && endTime.isAfter(other.startTime)) ||
               (other.startTime.isBefore(endTime) && other.endTime.isAfter(startTime));
    }
}
