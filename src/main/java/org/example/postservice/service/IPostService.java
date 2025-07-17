package org.example.postservice.service;

import org.example.postservice.model.dto.post.EditPost;
import org.example.postservice.model.dto.post.PostDTO;
import org.example.postservice.model.dto.post.PostRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IPostService {
    PostDTO CreatePost(PostRequest postRequest);

    Boolean UpdatePost(EditPost editPost);

    Boolean DeletePost(Long postId);

    PostDTO GetPost(Long postId);

    List<PostDTO> ListPostRandom(Integer page, Integer pageSize);

    List<PostDTO> ListPostsUser(UUID userId, Integer page, Integer pageSize);
}
