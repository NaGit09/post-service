package org.example.postservice.model.dto.commnet;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentsRequest {
    private String content;
    private String mediaUrl;

    private Long mediaId;
    private Long postId;
    private UUID userId;

    private String mediaType;
    private Long parentId;

}
