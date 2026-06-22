package com.example.socialmedia.Dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAuthorRequest {

    @Size(min = 5, message = "Full name must contain at least 5 characters")
    private String fullName;

    private String username;

    @Email(message = "Email must be valid")
    private String email;

    private String bio;
}
