package org.example.postservice.model.dto.post;


import lombok.Data;
import org.example.postservice.model.dto.orther.UserInforResponse;

import java.util.UUID;

@Data
public class PostDTO {
    private Long id;
    private UUID userId;
    private String content;
    private Boolean isLiked;
    private Boolean isComment;
    private Boolean isShare;
    private String modes;
    private UserInforResponse userInfor;
}
