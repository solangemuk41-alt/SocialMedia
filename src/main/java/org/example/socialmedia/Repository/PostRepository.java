package com.socialmedia.repository;

import com.socialmedia.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCreatedBy_Id(Long authorId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.createdBy.id = :authorId")
    void deleteByAuthorId(@Param("authorId") Long authorId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.createdAt BETWEEN :start AND :end")
    void deleteByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}