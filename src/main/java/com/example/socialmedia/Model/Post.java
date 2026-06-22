package com.example.socialmedia.Model;

import com.example.socialmedia.Model.Author;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public enum Visibility {
        PUBLIC,
        PRIVATE,
        FRIENDS_ONLY
    }
}