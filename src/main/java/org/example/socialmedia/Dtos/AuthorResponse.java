package com.socialmedia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {
    private String message;
    private String fullName;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}