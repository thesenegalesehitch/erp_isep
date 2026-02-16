package com.isep.repository;

import com.isep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByStudentNumber(String studentNumber);
    Boolean existsByEmail(String email);
    Boolean existsByStudentNumber(String studentNumber);
}

