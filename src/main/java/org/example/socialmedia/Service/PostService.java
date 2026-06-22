package com.socialmedia.service;

import com.socialmedia.dto.request.CreatePostRequest;
import com.socialmedia.dto.response.PostResponse;
import com.socialmedia.entity.Author;
import com.socialmedia.entity.Post;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.repository.PostRepository;
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
    private final AuthorService authorService;

    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        Author author = authorService.getAuthorById(request.getAuthorId());

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setVisibility("PUBLIC"); // default
        post.setCreatedBy(author);

        Post savedPost = postRepository.save(post);

        return new PostResponse(
                "Post created successfully",
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getVisibility(),
                savedPost.getCreatedAt(),
                savedPost.getCreatedBy().getFullName()
        );
    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


    public List<Post> getPostsByAuthor(Long authorId) {

        authorService.getAuthorById(authorId);
        return postRepository.findByCreatedBy_Id(authorId);
    }


    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    }


    @Transactional
    public PostResponse updatePost(Long postId, CreatePostRequest request) {
        Post post = getPostById(postId);

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        Post updatedPost = postRepository.save(post);

        return new PostResponse(
                "Post updated successfully",
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getVisibility(),
                updatedPost.getCreatedAt(),
                updatedPost.getCreatedBy().getFullName()
        );
    }

    @Transactional
    public String deletePostById(Long postId) {
        Post post = getPostById(postId);
        postRepository.delete(post);
        return "Post deleted successfully with id: " + postId;
    }


    @Transactional
    public String deletePostsByAuthor(Long authorId) {

        authorService.getAuthorById(authorId);
        postRepository.deleteByAuthorId(authorId);
        return "All posts deleted successfully for author id: " + authorId;
    }

    @Transactional
    public String deletePostsByDateRange(LocalDateTime start, LocalDateTime end) {
        postRepository.deleteByCreatedAtBetween(start, end);
        return "Posts deleted successfully between " + start + " and " + end;
    }
}