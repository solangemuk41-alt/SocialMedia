package com.socialmedia.controller;

import com.socialmedia.dto.request.CreatePostRequest;
import com.socialmedia.dto.response.PostResponse;
import com.socialmedia.entity.Post;
import com.socialmedia.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        return new ResponseEntity<>(postService.createPost(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Post>> getPostsByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(postService.getPostsByAuthor(authorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody CreatePostRequest request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.deletePostById(id));
    }

    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<String> deletePostsByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(postService.deletePostsByAuthor(authorId));
    }

    @DeleteMapping("/filter")
    public ResponseEntity<String> deletePostsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(postService.deletePostsByDateRange(start, end));
    }
}