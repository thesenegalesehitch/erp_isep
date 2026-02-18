package com.isep.repository;

import com.isep.model.StudentLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentLinkRepository extends JpaRepository<StudentLink, Long> {
    
    List<StudentLink> findByParentId(Long parentId);
    
    List<StudentLink> findByStudentId(Long studentId);
    
    Optional<StudentLink> findByParentIdAndStudentId(Long parentId, Long studentId);
    
    @Query("SELECT sl FROM StudentLink sl WHERE sl.verificationCode = :code AND sl.isVerified = false")
    Optional<StudentLink> findByVerificationCode(@Param("code") String code);
    
    @Query("SELECT COUNT(sl) FROM StudentLink sl WHERE sl.parent.id = :parentId AND sl.isActive = true")
    long countActiveLinksByParent(@Param("parentId") Long parentId);
    
    @Query("SELECT sl FROM StudentLink sl WHERE sl.parent.id = :parentId AND sl.isVerified = true AND sl.isActive = true")
    List<StudentLink> findVerifiedLinksByParent(@Param("parentId") Long parentId);
}
