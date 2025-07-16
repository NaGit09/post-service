package org.example.postservice.controller;

import org.example.postservice.model.dto.post.EditPost;
import org.example.postservice.model.dto.post.PostRequest;
import org.example.postservice.model.entity.Post;
import org.example.postservice.service.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-service/posts")
public class PostController {
    @Autowired
    public PostServiceImp postService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PostRequest postRequest) {
        return postService.CreatePost(postRequest);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody EditPost editPost) {
        return postService.UpdatePost(editPost);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return postService.DeletePost(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return postService.GetPost(id);
    }
}
