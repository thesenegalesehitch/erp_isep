package com.isep.controller;

import com.isep.model.Forum;
import com.isep.model.ForumPost;
import com.isep.model.User;
import com.isep.repository.ForumRepository;
import com.isep.repository.ForumPostRepository;
import com.isep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/forums")
@RequiredArgsConstructor
public class ForumController {
    
    private final ForumRepository forumRepository;
    private final ForumPostRepository forumPostRepository;
    private final UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<Forum>> getAllForums(
            @RequestParam(required = false) String specialty) {
        List<Forum> forums = specialty != null
            ? forumRepository.findBySpecialty(specialty)
            : forumRepository.findByIsActiveTrue();
        return ResponseEntity.ok(forums);
    }
    
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<ForumPost>> getForumPosts(@PathVariable Long id) {
        Forum forum = forumRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Forum not found"));
        List<ForumPost> posts = forumPostRepository.findByForum(forum);
        return ResponseEntity.ok(posts);
    }
    
    @PostMapping("/{id}/posts")
    public ResponseEntity<ForumPost> createPost(
            @PathVariable Long id,
            @Valid @RequestBody ForumPost post,
            Authentication authentication) {
        Forum forum = forumRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Forum not found"));
        User author = userRepository.findByEmail(authentication.getName())
            .orElseThrow();
        
        post.setForum(forum);
        post.setAuthor(author);
        return ResponseEntity.ok(forumPostRepository.save(post));
    }
    
    @PostMapping("/posts/{postId}/reply")
    public ResponseEntity<ForumPost> replyToPost(
            @PathVariable Long postId,
            @Valid @RequestBody ForumPost reply,
            Authentication authentication) {
        ForumPost parentPost = forumPostRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
        User author = userRepository.findByEmail(authentication.getName())
            .orElseThrow();
        
        reply.setForum(parentPost.getForum());
        reply.setAuthor(author);
        reply.setParentPost(parentPost);
        return ResponseEntity.ok(forumPostRepository.save(reply));
    }
}

