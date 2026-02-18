package com.isep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AttendanceRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    
    @Column(name = "attendance_date", nullable = false)
    private LocalDateTime attendanceDate;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
    
    @Column(name = "subject")
    private String subject;
    
    @Column(name = "teacher_name")
    private String teacherName;
    
    @Column(name = "notes")
    private String notes;
    
    @Column(name = "late_minutes")
    private Integer lateMinutes;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum AttendanceStatus {
        PRESENT,
        ABSENT_EXCUSED,
        ABSENT_UNEXCUSED,
        LATE,
        SICK_LEAVE
    }
    
    public boolean isAbsent() {
        return status == AttendanceStatus.ABSENT_EXCUSED || status == AttendanceStatus.ABSENT_UNEXCUSED;
    }
    
    public boolean isLate() {
        return status == AttendanceStatus.LATE;
    }
}
