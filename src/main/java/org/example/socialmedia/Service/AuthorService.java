package com.socialmedia.service;

import com.socialmedia.dto.request.CreateAuthorRequest;
import com.socialmedia.dto.response.AuthorResponse;
import com.socialmedia.entity.Author;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.repository.AuthorRepository;
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

    // 1. Add new author
    @Transactional
    public AuthorResponse createAuthor(CreateAuthorRequest request) {
        // Check if username already exists
        if (authorRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        // Check if email already exists
        if (authorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        Author author = new Author();
        author.setFullName(request.getFullName());
        author.setUsername(request.getUsername());
        author.setEmail(request.getEmail());

        Author savedAuthor = authorRepository.save(author);

        return new AuthorResponse(
                "Author created successfully",
                savedAuthor.getFullName(),
                savedAuthor.getUsername(),
                savedAuthor.getEmail(),
                savedAuthor.getCreatedAt()
        );
    }

    // 2. Get author by ID
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    // 3. Get author by username
    public Author getAuthorByUsername(String username) {
        return authorRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with username: " + username));
    }

    // 4. Get author by email
    public Author getAuthorByEmail(String email) {
        return authorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with email: " + email));
    }

    // 5. Get all authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // 6. Get authors created in specific period
    public List<Author> getAuthorsByDateRange(LocalDateTime start, LocalDateTime end) {
        return authorRepository.findByCreatedAtBetween(start, end);
    }

    // 7. Update author's bio only
    @Transactional
    public AuthorResponse updateAuthorBio(Long authorId, String bio) {
        Author author = getAuthorById(authorId);
        author.setBio(bio);
        Author updatedAuthor = authorRepository.save(author);

        return new AuthorResponse(
                "Author bio updated successfully",
                updatedAuthor.getFullName(),
                updatedAuthor.getUsername(),
                updatedAuthor.getEmail(),
                updatedAuthor.getCreatedAt()
        );
    }

    // 8. Update author's full profile (name, username, email)
    @Transactional
    public AuthorResponse updateAuthorProfile(Long authorId, CreateAuthorRequest request) {
        Author author = getAuthorById(authorId);

        // Check if new username is taken by another author
        if (!author.getUsername().equals(request.getUsername()) &&
                authorRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        // Check if new email is taken by another author
        if (!author.getEmail().equals(request.getEmail()) &&
                authorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        author.setFullName(request.getFullName());
        author.setUsername(request.getUsername());
        author.setEmail(request.getEmail());

        Author updatedAuthor = authorRepository.save(author);

        return new AuthorResponse(
                "Author profile updated successfully",
                updatedAuthor.getFullName(),
                updatedAuthor.getUsername(),
                updatedAuthor.getEmail(),
                updatedAuthor.getCreatedAt()
        );
    }

    // 9. Delete author
    @Transactional
    public String deleteAuthor(Long authorId) {
        Author author = getAuthorById(authorId);
        authorRepository.delete(author);
        return "Author deleted successfully with id: " + authorId;
    }
}