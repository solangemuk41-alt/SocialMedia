package com.example.socialmedia.Service;

import com.example.socialmedia.Dtos.*;
import com.example.socialmedia.Model.Author;
import com.example.socialmedia.Model.Post;
import com.example.socialmedia.exception.ResourceNotFoundException;
import com.example.socialmedia.Repository.AuthorRepository;
import com.example.socialmedia.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    // CREATE POST
    public PostResponse createPost(CreatePostRequest request) {

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + request.getAuthorId()));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .visibility(request.getVisibility())
                .createdBy(author)
                .build();

        Post saved = postRepository.save(post);
        return mapToResponse(saved, "Post created successfully");
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(p -> mapToResponse(p, ""))
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post not found with id: " + id));

        return mapToResponse(post, "Post found");
    }


    public List<PostResponse> getPostsByAuthor(Long authorId) {

        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Author not found with id: " + authorId);
        }

        return postRepository.findByCreatedById(authorId)
                .stream()
                .map(p -> mapToResponse(p, ""))
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsByPeriod(LocalDateTime start, LocalDateTime end) {
        return postRepository.findByCreatedAtBetween(start, end)
                .stream()
                .map(p -> mapToResponse(p, ""))
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post not found with id: " + id));

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            post.setTitle(request.getTitle());
        }

        if (request.getContent() != null && !request.getContent().isBlank()) {
            post.setContent(request.getContent());
        }

        return mapToResponse(postRepository.save(post), "Post updated successfully");
    }

    @Transactional
    public String deletePostById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post not found with id: " + id));

        postRepository.delete(post);

        return "Post '" + post.getTitle() + "' deleted successfully";
    }

    @Transactional
    public String deletePostsByAuthor(Long authorId) {

        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Author not found with id: " + authorId);
        }

        postRepository.deleteByCreatedById(authorId);

        return "All posts by author " + authorId + " deleted successfully";
    }

    @Transactional
    public String deletePostByIdAndAuthor(Long postId, Long authorId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post not found with id: " + postId));

        if (!post.getCreatedBy().getId().equals(authorId)) {
            throw new IllegalArgumentException(
                    "Author did not create this post");
        }

        postRepository.delete(post);

        return "Post deleted successfully";
    }


    @Transactional
    public String deletePostsByPeriod(LocalDateTime start, LocalDateTime end) {

        List<Post> posts = postRepository.findByCreatedAtBetween(start, end);

        postRepository.deleteAll(posts);

        return posts.size() + " posts deleted successfully";
    }

    private PostResponse mapToResponse(Post post, String message) {
        return PostResponse.builder()
                .message(message)
                .title(post.getTitle())
                .content(post.getContent())
                .visibility(post.getVisibility())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy().getFullName())
                .build();
    }
}