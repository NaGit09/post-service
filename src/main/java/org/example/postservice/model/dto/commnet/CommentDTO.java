package org.example.postservice.model.dto.commnet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long postId;
    private UUID userId;
    private String content;
    private String media;
    private LocalDateTime createdAt;
    private List<CommentDTO> replies = new ArrayList<>();

}
