package com.isep.repository;

import com.isep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByStudentNumber(String studentNumber);
    Boolean existsByEmail(String email);
    Boolean existsByStudentNumber(String studentNumber);
    
    boolean existsByEmailAndSchoolId(String email, Long schoolId);
    
    boolean existsByEmailAndSchoolIdAndRole(String email, Long schoolId, User.Role role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.school.id = :schoolId AND u.isActive = true")
    long countActiveUsersBySchool(@Param("schoolId") Long schoolId);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.school.id = :schoolId AND u.role = :role AND u.isActive = true")
    long countUsersBySchoolAndRole(@Param("schoolId") Long schoolId, @Param("role") User.Role role);
}

