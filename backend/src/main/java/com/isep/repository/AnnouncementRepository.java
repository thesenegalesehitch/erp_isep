package com.isep.repository;

import com.isep.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByIsPublishedTrueAndExpiresAtAfter(LocalDateTime date);
    List<Announcement> findByType(Announcement.AnnouncementType type);
    List<Announcement> findByPriority(Announcement.Priority priority);
}

