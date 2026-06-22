package com.example.socialmedia.Dtos;

import com.example.socialmedia.Model.Post.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private String message;
    private String title;
    private String content;
    private Visibility visibility;
    private LocalDateTime createdAt;
    private String createdBy;
}