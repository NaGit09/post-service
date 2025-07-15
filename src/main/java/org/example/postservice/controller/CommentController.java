package org.example.postservice.controller;

import org.example.postservice.model.dto.commnet.CommentEditRequest;
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


    @PostMapping("/create-comment")
    public ResponseEntity<?> getComments
            (@RequestBody  CommentsRequest commentsRequest) {
        return commentsService.CreateComment(commentsRequest);
    }

    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<?> DeleteComment(@PathVariable Long id) {
        return commentsService.DeleteComment(id);

    }

    @PutMapping("/update-comment")
    public ResponseEntity<?> UpdateComment
            (@RequestBody  CommentEditRequest commentEditRequest) {
        return ResponseEntity.ok(commentsService.UpdateComment(commentEditRequest));
    }

    @GetMapping("/get-all-comment/{postId}&{page}&{size}")
    public ResponseEntity<?> getAllComments(@PathVariable Long postId,
                                            @PathVariable Integer page,
                                            @PathVariable Integer size) {
        return commentsService.getTopLevelComments(postId, page, size);
    }
    @GetMapping("/get-replies-comment/{parentId}&{page}&{size}")
    public ResponseEntity<?> getAllRepComments(@PathVariable Long parentId,
                                            @PathVariable Integer page,
                                            @PathVariable Integer size) {
        return commentsService.getRepliesComments(parentId, page, size);
    }
    @GetMapping("/get-total-comment/{postId}")
    public ResponseEntity<?> getTotalComment(@PathVariable Long postId) {
        return commentsService.TotalCommentsInPost(postId);
    }

    @GetMapping("/get-total-replies/{parentCommentId}")
    public ResponseEntity<?> getTotalRepliesComments
            (@PathVariable Long parentCommentId) {
        return commentsService.TotalCommentReplies(parentCommentId);
    }

}
