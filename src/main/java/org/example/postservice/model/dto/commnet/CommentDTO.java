package org.example.postservice.model.dto.commnet;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CommentDTO {
    private Long id;
    private UUID userId;
    private String content;
    private LocalDateTime createdAt;
}
