package org.example.postservice.controller;

import org.example.postservice.model.dto.commnet.CommentDTO;
import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentRoot;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;
import org.example.postservice.service.CommentsServiceImp;
import org.example.postservice.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/post-service/comments")
public class CommentController {
    @Autowired
    private CommentsServiceImp commentsService;


    @PostMapping("/create")
    public ResponseEntity<?> getComments
            (@RequestBody CommentsRequest commentsRequest) {
        Long result = commentsService.CreateComment(commentsRequest);
        if (result == -1) {
            return GenerateResponse.generateErrorResponse
                    (401, "Create Comment Failed");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "Create comment successfully !", result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteComment(@PathVariable Long id) {
        Boolean result = commentsService.DeleteComment(id);
        if (!result) {
            return GenerateResponse.generateErrorResponse
                    (401, "Delete Comment Failed");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "Delete comment successfully !", id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> UpdateComment
            (@RequestBody CommentEditRequest commentEditRequest) {
        Boolean result = commentsService.UpdateComment(commentEditRequest);
        if (!result) {
            return GenerateResponse.generateErrorResponse
                    (401, "Update Comment Failed");
        }
        return GenerateResponse.generateSuccessResponse(
                200, "Update comment successfully !", true
        );
    }

    @GetMapping("/all/{postId}&{page}&{size}")
    public ResponseEntity<?> getAllComments
            (@PathVariable Long postId,
             @PathVariable Integer page,
             @PathVariable Integer size) {
        Page<CommentRoot> comments = commentsService.getTopLevelComments(postId, page, size);
        if (!comments.getContent().isEmpty()) {
            return GenerateResponse.generateErrorResponse(
                    200, "Not comments"
            );
        }
        return GenerateResponse.generateSuccessResponse(
                200, " Get comments successfully !", comments.getContent()
        );
    }

    @GetMapping("/replies/{parentId}&{page}&{size}")
    public ResponseEntity<?> getAllRepComments
            (@PathVariable Long parentId,
             @PathVariable Integer page,
             @PathVariable Integer size) {
        Page<CommentDTO> comments = commentsService.getRepliesComments(parentId, page, size);
        if (!comments.getContent().isEmpty()) {
            return GenerateResponse.generateErrorResponse
                    (200, "Not comments");
        }
        return GenerateResponse.generateSuccessResponse
                (200, " Get comments successfully !", comments.getContent());
    }

    @GetMapping("/total/{postId}")
    public ResponseEntity<?> getTotalComment
            (@PathVariable Long postId) {
        Integer result = commentsService.TotalCommentsInPost(postId);
        if (result == -1) {
            return  GenerateResponse.generateErrorResponse
                (401, "Total Comment Failed");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "Total comment successfully !", result);
    }

    @GetMapping("/total-replies/{parentCommentId}")
    public ResponseEntity<?> getTotalRepliesComments
            (@PathVariable Long parentCommentId) {
        Integer result = commentsService.TotalCommentReplies(parentCommentId);
        if (result == -1) {
            return GenerateResponse.generateErrorResponse
                (401, "Total Comment Failed");
        }
        return GenerateResponse.generateSuccessResponse
                (200, " Get comments successfully !", result);
    }

}
