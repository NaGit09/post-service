package org.example.postservice.model.dto.post;

import lombok.Data;

@Data
public class PostImageRequest {
    private String url ;
    private String publicId ;
    private Long postId;
}
