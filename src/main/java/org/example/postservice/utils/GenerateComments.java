package org.example.postservice.utils;


import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;


public class GenerateComments {
    public static Comments generateComments(CommentsRequest comments) {
        Comments comment = new Comments();
        comment.setPostId(comments.getPost_id());
        comment.setUserId(comments.getUser_id());
        comment.setContent(comments.getContent());
        comment.setParentCommentId(comments.getParentId());
        return comment;
    }
        public static Comments updateComment(Comments comments , CommentEditRequest editComment) {
            comments.setContent(editComment.getContent());
            comments.setMedia(editComment.getMedia());
            return comments;
        }
}
