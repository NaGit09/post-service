package org.example.postservice.service;



import org.example.postservice.model.dto.commnet.CommentDTO;
import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface ICommentsService {

    //   CRUD
    ResponseEntity<?> CreateComment(CommentsRequest commentsRequest);

    ResponseEntity<?> DeleteComment(Long commentId);

    ResponseEntity<?> UpdateComment(CommentEditRequest commentsEditRequest);

    // Return tree comment
    ResponseEntity<?> getPagedCommentTrees(Long postId, int page, int size);

    CommentDTO buildTreeRecursive(Comments comments);

    // return top comment
    ResponseEntity<?> getTopLevelComments(Long postId, int page, int size);

    // return total comment in post
    ResponseEntity<?> TotalCommentsInPost(Long postId);


}