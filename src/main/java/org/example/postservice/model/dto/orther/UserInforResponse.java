package org.example.postservice.model.dto.orther;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserInforResponse {
    private UUID userId;
    private String username;
    private String avatar_url;
}


