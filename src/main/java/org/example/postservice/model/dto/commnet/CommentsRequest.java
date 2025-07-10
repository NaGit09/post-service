package org.example.postservice.model.dto.commnet;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentsRequest {
    private Long post_id;
    private UUID user_id;
    private String content;
    private Long parentId;
}
