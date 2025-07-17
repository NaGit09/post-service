package org.example.postservice.service;


import org.example.postservice.model.dto.commnet.CommentDTO;
import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentRoot;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


import java.util.UUID;

public interface ICommentsService {
    //   CRUD
    Long CreateComment(CommentsRequest commentsRequest);

    Boolean DeleteComment(Long commentId);

    Boolean UpdateComment(CommentEditRequest commentsEditRequest);

    // return top comment
    Page<CommentRoot> getTopLevelComments(Long postId, int page, int size);

    // return replies comments
    Page<CommentDTO> getRepliesComments(Long CommentId, int page, int size);

    // get total func
   Integer TotalCommentsInPost(Long postId);

    Integer TotalCommentReplies(Long commentId);
}