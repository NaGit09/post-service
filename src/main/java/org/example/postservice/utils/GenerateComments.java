package org.example.postservice.utils;


import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;


public class GenerateComments {
    public static Comments generateComments(CommentsRequest comments) {

        Long commentParentId = (comments.getParentId() == null) ? null : comments.getParentId();

        return Comments.builder()
                .postId(comments.getPost_id())
                .userId(comments.getUser_id())
                .content(comments.getContent())
                .parentCommentId(commentParentId).build();
    }

    public static Comments updateComment(Comments comments, CommentEditRequest editComment) {
        comments.setContent(editComment.getContent());
        comments.setMedia(editComment.getMedia());
        return comments;
    }
}
