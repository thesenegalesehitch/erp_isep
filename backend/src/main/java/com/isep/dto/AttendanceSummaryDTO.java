package com.isep.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AttendanceSummaryDTO {
    private Map<Long, StudentStats> studentStats = new HashMap<>();
    
    @Data
    public static class StudentStats {
        private long presentDays;
        private long absentDays;
        private long lateDays;
        private double attendanceRate;
    }
}
