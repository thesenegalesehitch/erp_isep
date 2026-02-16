package com.isep.repository;

import com.isep.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationId(Long conversationId);
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}

