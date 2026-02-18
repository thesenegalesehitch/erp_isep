package com.isep.repository;

import com.isep.model.AttendanceRecord;
import com.isep.model.AttendanceRecord.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    
    List<AttendanceRecord> findByStudentId(Long studentId);
    
    List<AttendanceRecord> findBySchoolId(Long schoolId);
    
    List<AttendanceRecord> findByStudentIdAndAttendanceDateBetween(Long studentId, LocalDateTime start, LocalDateTime end);
    
    List<AttendanceRecord> findByStudentIdAndStatus(Long studentId, AttendanceStatus status);
    
    @Query("SELECT a FROM AttendanceRecord a WHERE a.student.id = :studentId AND a.attendanceDate BETWEEN :start AND :end ORDER BY a.attendanceDate DESC")
    List<AttendanceRecord> findByStudentInPeriod(@Param("studentId") Long studentId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT COUNT(a) FROM AttendanceRecord a WHERE a.student.id = :studentId AND a.status = 'PRESENT' AND a.attendanceDate BETWEEN :start AND :end")
    long countPresentDays(@Param("studentId") Long studentId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT COUNT(a) FROM AttendanceRecord a WHERE a.student.id = :studentId AND a.status IN ('ABSENT_EXCUSED', 'ABSENT_UNEXCUSED') AND a.attendanceDate BETWEEN :start AND :end")
    long countAbsentDays(@Param("studentId") Long studentId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT COUNT(a) FROM AttendanceRecord a WHERE a.student.id = :studentId AND a.status = 'LATE' AND a.attendanceDate BETWEEN :start AND :end")
    long countLateDays(@Param("studentId") Long studentId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT a FROM AttendanceRecord a WHERE a.student.id IN :studentIds AND a.attendanceDate BETWEEN :start AND :end")
    List<AttendanceRecord> findByStudentsInPeriod(@Param("studentIds") List<Long> studentIds, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
