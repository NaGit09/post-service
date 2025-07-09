package org.example.postservice.controller;

import org.example.postservice.model.dto.APIResponse;
import org.example.postservice.model.dto.ErrorResponse;
import org.example.postservice.model.dto.post.PostRequest;
import org.example.postservice.service.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-service/posts")
public class PostController {
    @Autowired
    public PostServiceImp postService;

    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest post) {
        if (!postService.CreatePost(post)) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.NO_CONTENT.value(),
                            "Error Creating Post")
            );
        }

        return ResponseEntity.ok().body(
                new APIResponse<>(
                        HttpStatus.OK.value() ,
                        "SUCCESS" ,
                        "SUCCESSFULLY CREATED POST"
                )
        );
    }

    @GetMapping
    public ResponseEntity<?> getPost(@PathVariable Integer id) {

        return ResponseEntity.ok().build();
    }

}
