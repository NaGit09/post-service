package org.example.postservice.service;

import org.example.postservice.model.dto.post.EditPost;
import org.example.postservice.model.dto.post.PostRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IPostService {
    ResponseEntity<?> CreatePost(PostRequest postRequest);

    ResponseEntity<?> UpdatePost(EditPost editPost);

    ResponseEntity<?> DeletePost(Long postId);

    ResponseEntity<?> GetPost(Long postId);

    ResponseEntity<?> ListPostRandom(Integer page, Integer pageSize);

    ResponseEntity<?> ListPostsUser(UUID userId, Integer page, Integer pageSize);
}
