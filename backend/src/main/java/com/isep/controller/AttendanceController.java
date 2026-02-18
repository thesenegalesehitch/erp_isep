package com.isep.controller;

import com.isep.model.AttendanceRecord;
import com.isep.service.AttendanceService;
import com.isep.dto.AttendanceRecordDTO;
import com.isep.dto.AttendanceSummaryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<AttendanceRecordDTO>> getStudentAttendance(
            @PathVariable Long parentId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        List<AttendanceRecord> records = attendanceService.getStudentAttendance(parentId, start, end);
        return ResponseEntity.ok(records.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    @GetMapping("/parent/{parentId}/summary")
    public ResponseEntity<AttendanceSummaryDTO> getAttendanceSummary(
            @PathVariable Long parentId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        AttendanceService.AttendanceSummary summary = attendanceService.getAttendanceSummary(parentId, start, end);
        return ResponseEntity.ok(convertToDTO(summary));
    }
    
    @GetMapping("/parent/{parentId}/absences")
    public ResponseEntity<List<AttendanceRecordDTO>> getRecentAbsences(
            @PathVariable Long parentId,
            @RequestParam(defaultValue = "30") int days) {
        List<AttendanceRecord> records = attendanceService.getRecentAbsences(parentId, days);
        return ResponseEntity.ok(records.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    private AttendanceRecordDTO convertToDTO(AttendanceRecord record) {
        AttendanceRecordDTO dto = new AttendanceRecordDTO();
        dto.setId(record.getId());
        dto.setStudentId(record.getStudent().getId());
        dto.setStudentName(record.getStudent().getFullName());
        dto.setSchoolId(record.getSchool().getId());
        dto.setSchoolName(record.getSchool().getName());
        dto.setAttendanceDate(record.getAttendanceDate());
        dto.setStatus(record.getStatus());
        dto.setSubject(record.getSubject());
        dto.setTeacherName(record.getTeacherName());
        dto.setNotes(record.getNotes());
        dto.setLateMinutes(record.getLateMinutes());
        dto.setCreatedAt(record.getCreatedAt());
        return dto;
    }
    
    private AttendanceSummaryDTO convertToDTO(AttendanceService.AttendanceSummary summary) {
        AttendanceSummaryDTO dto = new AttendanceSummaryDTO();
        
        for (var entry : summary.getStudentStats().entrySet()) {
            Long studentId = entry.getKey();
            AttendanceService.AttendanceSummary.StudentAttendanceStats stats = entry.getValue();
            
            AttendanceSummaryDTO.StudentStats studentStats = new AttendanceSummaryDTO.StudentStats();
            studentStats.setPresentDays(stats.getPresentDays());
            studentStats.setAbsentDays(stats.getAbsentDays());
            studentStats.setLateDays(stats.getLateDays());
            studentStats.setAttendanceRate(stats.getAttendanceRate());
            
            dto.getStudentStats().put(studentId, studentStats);
        }
        
        return dto;
    }
}
