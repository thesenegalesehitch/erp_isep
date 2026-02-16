package com.isep.repository;

import com.isep.model.Forum;
import com.isep.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    List<ForumPost> findByForum(Forum forum);
    List<ForumPost> findByParentPostIsNull();
    List<ForumPost> findByParentPostId(Long parentPostId);
}

