package org.example.postservice.model.dto.commnet;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentsRequest {
    private Long mediaId;
    private Long postId;
    private UUID userId;
    private String content;
    private String mediaUrl;
    private String mediaType;
    private Long parentId;
}
