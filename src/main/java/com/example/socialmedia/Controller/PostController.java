package com.example.socialmedia.Controller;

import com.example.socialmedia.Dtos.*;
import com.example.socialmedia.Service.PostService;
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
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody CreatePostRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<PostResponse>> getPostsByAuthor(
            @PathVariable Long authorId) {

        return ResponseEntity.ok(postService.getPostsByAuthor(authorId));
    }

    @GetMapping("/period")
    public ResponseEntity<List<PostResponse>> getPostsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return ResponseEntity.ok(postService.getPostsByPeriod(start, end));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostRequest request) {

        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {

        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<Void> deletePostsByAuthor(@PathVariable Long authorId) {

        postService.deletePostsByAuthor(authorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}/author/{authorId}")
    public ResponseEntity<Void> deletePostByIdAndAuthor(
            @PathVariable Long postId,
            @PathVariable Long authorId) {

        postService.deletePostByIdAndAuthor(postId, authorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/period")
    public ResponseEntity<Void> deletePostsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        postService.deletePostsByPeriod(start, end);
        return ResponseEntity.noContent().build();
    }
}