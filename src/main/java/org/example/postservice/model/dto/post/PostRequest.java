package org.example.postservice.model.dto.post;

import lombok.Data;
import org.example.postservice.model.entity.PostImage;

import java.util.List;
import java.util.UUID;

@Data
public class PostRequest {
    private UUID userId;
    private String content;
    private Boolean isLiked;
    private Boolean isComment;
    private Boolean isShare;
    private Boolean isSaved ;
    private String modes;
    private List<PostImageRequest> postRequestImages;
}
