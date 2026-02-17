package com.isep.repository;

import com.isep.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    List<Forum> findBySpecialty(String specialty);
    List<Forum> findByIsActiveTrue();
    
    @Query("SELECT f FROM Forum f WHERE f.isActive = true AND (LOWER(f.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(f.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Forum> searchByQuery(@Param("query") String query);
    
    @Query("SELECT DISTINCT f.specialty FROM Forum f WHERE f.specialty IS NOT NULL")
    List<String> findDistinctSpecialties();
}

