package com.isep.repository;

import com.isep.model.StudentGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGradeRepository extends JpaRepository<StudentGrade, Long> {
    
    List<StudentGrade> findByStudentId(Long studentId);
    
    List<StudentGrade> findBySchoolId(Long schoolId);
    
    List<StudentGrade> findByStudentIdAndAcademicYear(Long studentId, String academicYear);
    
    List<StudentGrade> findByStudentIdAndSemester(Long studentId, String semester);
    
    @Query("SELECT g FROM StudentGrade g WHERE g.student.id = :studentId AND g.academicYear = :year AND g.semester = :semester ORDER BY g.subject")
    List<StudentGrade> findByStudentForSemester(@Param("studentId") Long studentId, @Param("year") String year, @Param("semester") String semester);
    
    @Query("SELECT AVG(g.percentage) FROM StudentGrade g WHERE g.student.id = :studentId AND g.academicYear = :year AND g.semester = :semester")
    Double calculateAverageForSemester(@Param("studentId") Long studentId, @Param("year") String year, @Param("semester") String semester);
    
    @Query("SELECT g FROM StudentGrade g WHERE g.student.id IN :studentIds AND g.academicYear = :year AND g.semester = :semester")
    List<StudentGrade> findByStudentsForSemester(@Param("studentIds") List<Long> studentIds, @Param("year") String year, @Param("semester") String semester);
}
