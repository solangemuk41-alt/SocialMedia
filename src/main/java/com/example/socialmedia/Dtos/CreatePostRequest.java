package com.example.socialmedia.Dtos;

import com.example.socialmedia.Model.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 50,
            message = "Title must be between 10 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 5, max = 100,
            message = "Content must be between 50 and 2000 characters")
    private String content;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    private Post.Visibility visibility = Post.Visibility.PUBLIC;


}