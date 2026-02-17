package com.isep.repository;

import com.isep.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByIsPublishedTrueAndExpiresAtAfter(LocalDateTime date);
    List<Announcement> findByType(Announcement.AnnouncementType type);
    List<Announcement> findByPriority(Announcement.Priority priority);
    
    @Query("SELECT a FROM Announcement a WHERE a.isPublished = true AND (LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(a.content) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Announcement> searchByQuery(@Param("query") String query);
}

