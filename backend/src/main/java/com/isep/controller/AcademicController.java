package com.isep.controller;

import com.isep.model.StudentGrade;
import com.isep.service.AcademicService;
import com.isep.dto.StudentGradeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/academics")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AcademicController {
    
    private final AcademicService academicService;
    
    @GetMapping("/parent/{parentId}/grades")
    public ResponseEntity<List<StudentGradeDTO>> getStudentGrades(
            @PathVariable Long parentId,
            @RequestParam String academicYear,
            @RequestParam String semester) {
        List<StudentGrade> grades = academicService.getStudentGrades(parentId, academicYear, semester);
        return ResponseEntity.ok(grades.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    @GetMapping("/parent/{parentId}/grades/subject")
    public ResponseEntity<List<StudentGradeDTO>> getStudentGradesForSubject(
            @PathVariable Long parentId,
            @RequestParam String subject,
            @RequestParam String academicYear) {
        List<StudentGrade> grades = academicService.getStudentGradesForSubject(parentId, subject, academicYear);
        return ResponseEntity.ok(grades.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    @GetMapping("/student/{studentId}/average")
    public ResponseEntity<Double> calculateAverageForStudent(
            @PathVariable Long studentId,
            @RequestParam String academicYear,
            @RequestParam String semester) {
        Double average = academicService.calculateAverageForStudent(studentId, academicYear, semester);
        return ResponseEntity.ok(average);
    }
    
    @GetMapping("/parent/{parentId}/grades/recent")
    public ResponseEntity<List<StudentGradeDTO>> getRecentGrades(
            @PathVariable Long parentId,
            @RequestParam(defaultValue = "10") int limit) {
        List<StudentGrade> grades = academicService.getRecentGrades(parentId, limit);
        return ResponseEntity.ok(grades.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    private StudentGradeDTO convertToDTO(StudentGrade grade) {
        StudentGradeDTO dto = new StudentGradeDTO();
        dto.setId(grade.getId());
        dto.setStudentId(grade.getStudent().getId());
        dto.setStudentName(grade.getStudent().getFullName());
        dto.setSchoolId(grade.getSchool().getId());
        dto.setSchoolName(grade.getSchool().getName());
        dto.setSubject(grade.getSubject());
        dto.setExamType(grade.getExamType());
        dto.setGrade(grade.getGrade());
        dto.setMaxGrade(grade.getMaxGrade());
        dto.setCoefficient(grade.getCoefficient());
        dto.setSemester(grade.getSemester());
        dto.setAcademicYear(grade.getAcademicYear());
        dto.setComments(grade.getComments());
        dto.setGradeDate(grade.getGradeDate());
        dto.setPercentage(grade.getPercentage());
        dto.setIsPassing(grade.isPassing());
        dto.setCreatedAt(grade.getCreatedAt());
        return dto;
    }
}
