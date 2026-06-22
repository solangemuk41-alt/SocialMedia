package com.example.socialmedia.Service;

import com.example.socialmedia.Dtos.*;
import com.example.socialmedia.Model.Author;
import com.example.socialmedia.exception.ResourceNotFoundException;
import com.example.socialmedia.Repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorResponse createAuthor(CreateAuthorRequest request) {

        validateUniqueUsernameAndEmail(request.getUsername(), request.getEmail());

        Author author = Author.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .bio(request.getPost())
                .build();

        Author saved = authorRepository.save(author);
        return mapToResponse(saved, "Author created successfully");
    }


    public AuthorResponse getAuthorById(Long id) {
        return mapToResponse(findAuthorById(id), "Author found");
    }

    public AuthorResponse getAuthorByUsername(String username) {
        Author author = authorRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with username: " + username));

        return mapToResponse(author, "Author found");
    }


    public AuthorResponse getAuthorByEmail(String email) {
        Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with email: " + email));

        return mapToResponse(author, "Author found");
    }

    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(a -> mapToResponse(a, ""))
                .collect(Collectors.toList());
    }

    public List<AuthorResponse> getAuthorsByPeriod(LocalDateTime start, LocalDateTime end) {
        return authorRepository.findByCreatedAtBetween(start, end)
                .stream()
                .map(a -> mapToResponse(a, ""))
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorResponse updateBio(Long id, String bio) {
        Author author = findAuthorById(id);
        author.setBio(bio);

        return mapToResponse(authorRepository.save(author), "Bio updated successfully");
    }

    @Transactional
    public AuthorResponse updateAuthor(Long id, UpdateAuthorRequest request) {

        Author author = findAuthorById(id);

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            author.setFullName(request.getFullName());
        }

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            if (!request.getUsername().equals(author.getUsername())
                    && authorRepository.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException("Username already taken");
            }
            author.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!request.getEmail().equals(author.getEmail())
                    && authorRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already registered");
            }
            author.setEmail(request.getEmail());
        }

        if (request.getBio() != null) {
            author.setBio(request.getBio());
        }

        return mapToResponse(authorRepository.save(author),
                "Author profile updated successfully");
    }

    @Transactional
    public String deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        authorRepository.delete(author);

        return "Author '" + author.getUsername() + "' deleted successfully";
    }

    private Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + id));
    }

    private void validateUniqueUsernameAndEmail(String username, String email) {
        if (authorRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (authorRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
    }

    private AuthorResponse mapToResponse(Author author, String message) {
        return AuthorResponse.builder()
                .message(message)
                .fullName(author.getFullName())
                .username(author.getUsername())
                .email(author.getEmail())
                .createdAt(author.getCreatedAt())
                .build();
    }
}