package com.isep.repository;

import com.isep.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    List<Forum> findBySpecialty(String specialty);
    List<Forum> findByIsActiveTrue();
}

