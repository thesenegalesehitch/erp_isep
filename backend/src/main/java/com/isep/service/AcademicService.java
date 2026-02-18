package com.isep.service;

import com.isep.model.StudentGrade;
import com.isep.repository.StudentGradeRepository;
import com.isep.repository.StudentLinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AcademicService {
    
    private final StudentGradeRepository gradeRepository;
    private final StudentLinkRepository studentLinkRepository;
    
    public List<StudentGrade> getStudentGrades(Long parentId, String academicYear, String semester) {
        List<Long> studentIds = studentLinkRepository.findVerifiedLinksByParent(parentId)
            .stream()
            .map(link -> link.getStudent().getId())
            .collect(Collectors.toList());
        
        return gradeRepository.findByStudentsForSemester(studentIds, academicYear, semester);
    }
    
    public List<StudentGrade> getStudentGradesForSubject(Long parentId, String subject, String academicYear) {
        List<Long> studentIds = studentLinkRepository.findVerifiedLinksByParent(parentId)
            .stream()
            .map(link -> link.getStudent().getId())
            .collect(Collectors.toList());
        
        return gradeRepository.findByStudentsForSemester(studentIds, academicYear, "ALL")
            .stream()
            .filter(grade -> grade.getSubject().equals(subject))
            .collect(Collectors.toList());
    }
    
    public Double calculateAverageForStudent(Long studentId, String academicYear, String semester) {
        return gradeRepository.calculateAverageForSemester(studentId, academicYear, semester);
    }
    
    public List<StudentGrade> getRecentGrades(Long parentId, int limit) {
        List<Long> studentIds = studentLinkRepository.findVerifiedLinksByParent(parentId)
            .stream()
            .map(link -> link.getStudent().getId())
            .collect(Collectors.toList());
        
        return gradeRepository.findByStudentsForSemester(studentIds, "2023-2024", "ALL")
            .stream()
            .sorted((g1, g2) -> g2.getCreatedAt().compareTo(g1.getCreatedAt()))
            .limit(limit)
            .collect(Collectors.toList());
    }
}
