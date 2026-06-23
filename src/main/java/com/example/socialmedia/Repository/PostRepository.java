package com.example.socialmedia.Repository;

import com.example.socialmedia.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor_Id(Long authorId);

    void deleteByAuthor_Id(Long authorId);

    List<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}