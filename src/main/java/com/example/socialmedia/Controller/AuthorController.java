package com.example.socialmedia.Controller;

import com.example.socialmedia.Dtos.AuthorResponse;
import com.example.socialmedia.Dtos.CreateAuthorRequest;
import com.example.socialmedia.Dtos.UpdateAuthorRequest;
import com.example.socialmedia.Service.AuthorService;
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
    public ResponseEntity<AuthorResponse> createAuthor(
            @Valid @RequestBody CreateAuthorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.createAuthor(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AuthorResponse> getAuthorByUsername(
            @PathVariable String username) {

        return ResponseEntity.ok(authorService.getAuthorByUsername(username));
    }

    @GetMapping("/search")
    public ResponseEntity<AuthorResponse> getAuthorByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(authorService.getAuthorByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/period")
    public ResponseEntity<List<AuthorResponse>> getAuthorsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return ResponseEntity.ok(authorService.getAuthorsByPeriod(start, end));
    }

    @PatchMapping("/{id}/bio")
    public ResponseEntity<AuthorResponse> updateBio(
            @PathVariable Long id,
            @RequestBody String bio) {          // ✅ String directly — not UpdatePostRequest

        return ResponseEntity.ok(authorService.updateBio(id, bio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAuthorRequest request) {

        return ResponseEntity.ok(authorService.updateAuthor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}