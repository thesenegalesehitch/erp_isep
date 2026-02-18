package com.isep.service;

import com.isep.model.AttendanceRecord;
import com.isep.repository.AttendanceRecordRepository;
import com.isep.repository.StudentLinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AttendanceService {
    
    private final AttendanceRecordRepository attendanceRepository;
    private final StudentLinkRepository studentLinkRepository;
    
    public List<AttendanceRecord> getStudentAttendance(Long parentId, LocalDateTime start, LocalDateTime end) {
        List<Long> studentIds = studentLinkRepository.findVerifiedLinksByParent(parentId)
            .stream()
            .map(link -> link.getStudent().getId())
            .collect(Collectors.toList());
        
        return attendanceRepository.findByStudentsInPeriod(studentIds, start, end);
    }
    
    public AttendanceSummary getAttendanceSummary(Long parentId, LocalDateTime start, LocalDateTime end) {
        List<Long> studentIds = studentLinkRepository.findVerifiedLinksByParent(parentId)
            .stream()
            .map(link -> link.getStudent().getId())
            .collect(Collectors.toList());
        
        AttendanceSummary summary = new AttendanceSummary();
        
        for (Long studentId : studentIds) {
            long presentDays = attendanceRepository.countPresentDays(studentId, start, end);
            long absentDays = attendanceRepository.countAbsentDays(studentId, start, end);
            long lateDays = attendanceRepository.countLateDays(studentId, start, end);
            
            long totalDays = presentDays + absentDays + lateDays;
            double attendanceRate = totalDays > 0 ? (double) presentDays / totalDays * 100 : 0;
            
            summary.addStudentStats(studentId, presentDays, absentDays, lateDays, attendanceRate);
        }
        
        return summary;
    }
    
    public List<AttendanceRecord> getRecentAbsences(Long parentId, int days) {
        LocalDateTime start = LocalDateTime.now().minusDays(days);
        LocalDateTime end = LocalDateTime.now();
        
        List<Long> studentIds = studentLinkRepository.findVerifiedLinksByParent(parentId)
            .stream()
            .map(link -> link.getStudent().getId())
            .collect(Collectors.toList());
        
        return attendanceRepository.findByStudentsInPeriod(studentIds, start, end)
            .stream()
            .filter(record -> record.isAbsent())
            .collect(Collectors.toList());
    }
    
    public static class AttendanceSummary {
        private Map<Long, StudentAttendanceStats> studentStats = new HashMap<>();
        
        public void addStudentStats(Long studentId, long presentDays, long absentDays, long lateDays, double attendanceRate) {
            studentStats.put(studentId, new StudentAttendanceStats(presentDays, absentDays, lateDays, attendanceRate));
        }
        
        public Map<Long, StudentAttendanceStats> getStudentStats() {
            return studentStats;
        }
        
        public static class StudentAttendanceStats {
            private final long presentDays;
            private final long absentDays;
            private final long lateDays;
            private final double attendanceRate;
            
            public StudentAttendanceStats(long presentDays, long absentDays, long lateDays, double attendanceRate) {
                this.presentDays = presentDays;
                this.absentDays = absentDays;
                this.lateDays = lateDays;
                this.attendanceRate = attendanceRate;
            }
            
            public long getPresentDays() { return presentDays; }
            public long getAbsentDays() { return absentDays; }
            public long getLateDays() { return lateDays; }
            public double getAttendanceRate() { return attendanceRate; }
        }
    }
}
