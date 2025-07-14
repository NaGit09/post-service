package org.example.postservice.model.dto.orther;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String avatarUrl;
    private String fullName;
}

