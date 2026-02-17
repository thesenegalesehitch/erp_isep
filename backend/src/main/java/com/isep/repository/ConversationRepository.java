package com.isep.repository;

import com.isep.model.Conversation;
import com.isep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
    @Query("SELECT c FROM Conversation c WHERE c.type = 'PRIVATE' AND :user1 MEMBER OF c.participants AND :user2 MEMBER OF c.participants")
    List<Conversation> findPrivateConversationsBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);
    
    List<Conversation> findByParticipantsContaining(User user);
}

