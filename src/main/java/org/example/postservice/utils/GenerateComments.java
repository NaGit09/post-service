package org.example.postservice.utils;


import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comment;

public class GenerateComments {
    public static Comment generateComments(CommentsRequest comments) {
        Comment comment = new Comment();
        comment.setPostId(comments.getPost_id());
        comment.setUserId(comments.getUser_id());
        comment.setContent(comments.getContent());
        comment.setParentCommentId(comments.getParentId());
        return comment;
    }
}
