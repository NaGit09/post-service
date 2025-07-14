package org.example.postservice.model.dto.post;

import lombok.Data;
import org.example.postservice.model.entity.PostImage;

import java.util.List;

@Data
public class EditPost {
    private Long postId;

    private String content ;

    private Boolean isComment;
    private Boolean isShare;
    private Boolean isLike;
    private Boolean isSaved;

    private String mode;

    private List<PostImageRequest> postImageList;

}
