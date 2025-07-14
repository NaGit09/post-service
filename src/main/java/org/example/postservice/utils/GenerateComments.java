package org.example.postservice.utils;


import org.example.postservice.model.dto.commnet.CommentDTO;
import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;


public class GenerateComments {
    public static Comments generateComments(CommentsRequest comments) {

        Long commentParentId = (comments.getParentId() == null) ? null : comments.getParentId();

        return Comments.builder()
                .postId(comments.getPostId())
                .userId(comments.getUserId())
                .mediaId(comments.getMediaId())
                .mediaUrl(comments.getMediaUrl())
                .mediaType(comments.getMediaType())
                .content(comments.getContent())
                .parentCommentId(commentParentId).build();
    }
    public static CommentDTO generateCommentsDto(Comments comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .mediaUrl(comment.getMediaUrl())
                .mediaType(comment.getMediaType())
                .createdAt(comment.getCreatedAt())
                .build();
    }
    public static Comments updateComment(Comments comments, CommentEditRequest editComment) {
        comments.setContent(editComment.getContent());
        comments.setMediaId(editComment.getMediaId());
        comments.setMediaUrl(editComment.getMediaUrl());
        return comments;
    }
}
