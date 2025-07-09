package org.example.postservice.utils;

import org.example.postservice.model.dto.post.PostRequest;
import org.example.postservice.model.entity.Post;

public class GeneratePost {
    public static Post generatePost(PostRequest postRequest) {
        Post p = new Post();
        p.setContent(postRequest.getContent());

        p.setIsLike(postRequest.getIsLiked());
        p.setIsComment(postRequest.getIsComment());
        p.setIsShare(postRequest.getIsShare());
        p.setModes(postRequest.getModes());
        p.setUserId(postRequest.getUserId());

        return p;
    }
}
