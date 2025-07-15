package org.example.postservice.model.dto.commnet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRoot {
    private Long id;
    private String content;
    private String mediaUrl;
    private String mediaType;
    private LocalDateTime createdAt;
    private Integer totalComments;
}
