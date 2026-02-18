package com.isep.dto;

import com.isep.model.AttendanceRecord;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordDTO {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long schoolId;
    private String schoolName;
    private LocalDateTime attendanceDate;
    private AttendanceRecord.AttendanceStatus status;
    private String subject;
    private String teacherName;
    private String notes;
    private Integer lateMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
