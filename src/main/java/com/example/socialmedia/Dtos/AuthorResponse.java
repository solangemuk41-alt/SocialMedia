package com.example.socialmedia.Dtos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponse {

    private String message;
    private String fullName;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}
