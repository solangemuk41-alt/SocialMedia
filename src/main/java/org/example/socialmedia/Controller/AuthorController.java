package com.socialmedia.controller;

import com.socialmedia.dto.request.CreateAuthorRequest;
import com.socialmedia.dto.response.AuthorResponse;
import com.socialmedia.entity.Author;
import com.socialmedia.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        return new ResponseEntity<>(authorService.createAuthor(request), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Author> getAuthorByUsername(@PathVariable String username) {
        return ResponseEntity.ok(authorService.getAuthorByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Author> getAuthorByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authorService.getAuthorByEmail(email));
    }


    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }


    @GetMapping("/filter")
    public ResponseEntity<List<Author>> getAuthorsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(authorService.getAuthorsByDateRange(start, end));
    }


    @PatchMapping("/{id}/bio")
    public ResponseEntity<AuthorResponse> updateAuthorBio(
            @PathVariable Long id,
            @RequestBody String bio) {
        return ResponseEntity.ok(authorService.updateAuthorBio(id, bio));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthorProfile(
            @PathVariable Long id,
            @Valid @RequestBody CreateAuthorRequest request) {
        return ResponseEntity.ok(authorService.updateAuthorProfile(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.deleteAuthor(id));
    }
}