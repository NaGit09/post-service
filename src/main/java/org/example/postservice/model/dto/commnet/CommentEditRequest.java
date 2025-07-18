package org.example.postservice.model.dto.commnet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEditRequest {
    private Long id;
    private Long mediaId;
    private String content;
    private String mediaUrl;
}
