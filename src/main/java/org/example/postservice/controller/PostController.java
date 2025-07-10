package org.example.postservice.controller;


import org.example.postservice.service.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-service/posts")
public class PostController {
    @Autowired
    public PostServiceImp postService;



}
