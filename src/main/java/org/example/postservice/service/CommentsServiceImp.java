package org.example.postservice.service;

import org.example.postservice.model.dto.commnet.CommentDTO;
import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;
import org.example.postservice.model.repository.CommentsRepository;
import org.example.postservice.utils.GenerateComments;
import org.example.postservice.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CommentsServiceImp implements ICommentsService {

    @Autowired
    private CommentsRepository commentsRepository;


    @Override
    public ResponseEntity<?> CreateComment(CommentsRequest commentsRequest) {
        Comments comments = GenerateComments.generateComments(commentsRequest);
        commentsRepository.save(comments);
        return GenerateResponse.generateSuccessResponse(
                200, "comment created !", true);
    }

    @Override
    public ResponseEntity<?> DeleteComment(Long commentId) {
        if (commentId == null) {
            return GenerateResponse.generateErrorResponse(402, "comment id is null !");
        }
        commentsRepository.deleteById(commentId);
        return GenerateResponse.generateSuccessResponse
                (200, "comment deleted !", true);
    }

    @Override
    public ResponseEntity<?> UpdateComment(CommentEditRequest commentsEditRequest) {
        Optional<Comments> comments = commentsRepository.findById(commentsEditRequest.getId());
        if (comments.isEmpty()) {
            return GenerateResponse.generateErrorResponse(404, "comment not found !");
        }
        Comments newComment = GenerateComments.updateComment(comments.get(), commentsEditRequest);
        commentsRepository.save(newComment);
        return GenerateResponse.generateSuccessResponse
                (200, "comment updated !", true);
    }

    @Override
    public ResponseEntity<?> getPagedCommentTrees(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comments> topLevelComments = commentsRepository.findByPostIdAndParentCommentIdIsNull(postId, pageable);

        List<CommentDTO> treeDTOs = topLevelComments.getContent().stream()
                .map(this::buildTreeRecursive)
                .collect(Collectors.toList());

        return GenerateResponse.generateSuccessResponse(
                200, "Get comment successfully !",
                new PageImpl<>(treeDTOs, pageable, topLevelComments.getTotalElements()));
    }

    @Override
    public CommentDTO buildTreeRecursive(Comments comment) {
        CommentDTO dto = CommentDTO.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .media(comment.getMedia())
                .createdAt(comment.getCreatedAt())
                .build();

        List<Comments> replies = commentsRepository.findByParentCommentId(comment.getId());
        List<CommentDTO> replyDtos = replies.stream()
                .map(this::buildTreeRecursive)
                .collect(Collectors.toList());

        dto.setReplies(replyDtos);
        return dto;
    }

    @Override
    public ResponseEntity<?> getTopLevelComments(Long postId, int page, int size) {
        if (postId == null) {
            return GenerateResponse.generateErrorResponse(402, "post id is null !");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comments> topLevelComments = commentsRepository.findByPostIdAndParentCommentIdIsNull(postId, pageable);
        return GenerateResponse.generateSuccessResponse(
                200, "Get comment successfully !",
                new PageImpl<>(topLevelComments.getContent(), pageable, topLevelComments.getTotalElements())
        );
    }

    @Override
    public ResponseEntity<?> TotalCommentsInPost(Long postId) {
        if (postId == null) {
            return GenerateResponse.generateErrorResponse(402, "post id is null !");
        }
        Integer totalComments = commentsRepository.countByPostId(postId);
        return GenerateResponse.generateSuccessResponse(
                200, "get totalComments successfully !", totalComments
        );
    }
}
