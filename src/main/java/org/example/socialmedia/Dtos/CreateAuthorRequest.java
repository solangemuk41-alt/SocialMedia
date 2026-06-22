package com.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAuthorRequest {
    private String fullName;
    private String username;
    private String email;
}