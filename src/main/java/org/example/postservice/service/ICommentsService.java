package org.example.postservice.service;


import org.example.postservice.model.dto.commnet.CommentDTO;
import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;
import org.springframework.http.ResponseEntity;


import java.util.UUID;

public interface ICommentsService {
    //   CRUD
    ResponseEntity<?> CreateComment(CommentsRequest commentsRequest);

    ResponseEntity<?> DeleteComment(Long commentId);

    ResponseEntity<?> UpdateComment(CommentEditRequest commentsEditRequest);
    // return top comment
    ResponseEntity<?> getTopLevelComments(Long postId, int page, int size);
    // return replies comments
    ResponseEntity<?> getRepliesComments(Long CommentId, int page, int size);
    // get total func
    ResponseEntity<?> TotalCommentsInPost(Long postId);

    ResponseEntity<?> TotalCommentReplies(Long commentId);
}