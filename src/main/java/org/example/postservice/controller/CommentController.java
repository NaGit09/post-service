package org.example.postservice.controller;

import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.service.CommentsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/post-service/comments")
public class CommentController {
    @Autowired
    private CommentsServiceImp commentsService;

    @PostMapping("/sent-comment")
    public ResponseEntity<?> sentComments(@RequestBody CommentsRequest comments) {
        return commentsService.sentComments(comments);
    }

    @GetMapping("/getCommentUser")
    public ResponseEntity<?> getCommentUser(@RequestParam("userId") UUID userId) {
        return commentsService.getCommentsUsers(userId);
    }

    @GetMapping("/getCommentsPost/{postId}")
    public ResponseEntity<?> getCommentsPost( @PathVariable Integer postId) {
        return commentsService.getCommentsPost(postId);
    }
}
