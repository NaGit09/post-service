package org.example.postservice.controller;

import org.example.postservice.model.dto.post.EditPost;
import org.example.postservice.model.dto.post.PostDTO;
import org.example.postservice.model.dto.post.PostRequest;
import org.example.postservice.model.entity.Post;
import org.example.postservice.service.PostServiceImp;
import org.example.postservice.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-service/posts")
public class PostController {
    @Autowired
    public PostServiceImp postService;

    @PostMapping("/create")
    public ResponseEntity<?> create
            (@RequestBody PostRequest postRequest) {
        PostDTO postDTO = postService.CreatePost(postRequest);
        if (postDTO == null) {
            return GenerateResponse.generateErrorResponse
                    (401, "Generate Post Error");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "Create post successfully !", postDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update
            (@RequestBody EditPost editPost) {
        Boolean result = postService.UpdatePost(editPost);
        if (!result) {
            return GenerateResponse.generateErrorResponse
                    (401, "Update Post Error");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "Update post successfully !", true);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete
            (@PathVariable Long id) {
        Boolean result = postService.DeletePost(id);
        if (!result) {
            return GenerateResponse.generateErrorResponse
                  (401, "Delete Post Error");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "Delete post successfully !", true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get
            (@PathVariable Long id) {
        PostDTO postDTO = postService.GetPost(id);
        if (postDTO == null) {
            return GenerateResponse.generateErrorResponse
                 (401, "Get Post Error");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "Get post successfully !", postDTO);
    }
}
