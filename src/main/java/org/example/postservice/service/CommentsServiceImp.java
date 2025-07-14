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
                200, "comment created !", comments.getId());
    }

    @Override
    public ResponseEntity<?> DeleteComment(Long commentId) {
        if (commentId == null) {
            return GenerateResponse.generateErrorResponse(402, "comment id is null !");
        }
        Comments comments = commentsRepository.findById(commentId).orElse(null);
        if (comments == null) {
            return GenerateResponse.generateErrorResponse(402, "comment  is not existed !");
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
    public ResponseEntity<?> getPagedCommentTrees(Long parentCommentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        // Lấy các comment cấp cao nhất (parent comment)
        Page<Comments> topLevelComments = commentsRepository.findById(parentCommentId, pageable);

        List<CommentDTO> treeDTOs = topLevelComments.getContent().stream()
                .map(reply -> buildTreeRecursive(reply, page, size))
                .collect(Collectors.toList());

        return GenerateResponse.generateSuccessResponse(
                200, "Get comment successfully!",
                new PageImpl<>(treeDTOs, pageable, topLevelComments.getTotalElements()));

    }

    @Override
    public CommentDTO buildTreeRecursive
            (Comments comment, int replyPage, int replySize) {

        CommentDTO dto = GenerateComments.generateCommentsDto(comment);

        // Phân trang comment con (replies)
        Pageable replyPageable = PageRequest.of
                (replyPage, replySize, Sort.by("createdAt").ascending());

        Page<Comments> repliesPage = commentsRepository.
                findByParentCommentId(comment.getId(), replyPageable);

        // Lấy các comment con đã phân trang
        List<CommentDTO> replyDtos = repliesPage.getContent().stream()
                .map(reply -> buildTreeRecursive(reply, replyPage, replySize))
                .collect(Collectors.toList());

        dto.setReplies(replyDtos);

        dto.setHasMoreReplies(repliesPage.hasNext());

        return dto;
    }


    @Override
    public ResponseEntity<?> getTopLevelComments(Long postId, int page, int size) {
        if (postId == null) {
            return GenerateResponse.generateErrorResponse(402, "post id is null !");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Comments> topLevelComments = commentsRepository.
                findByPostIdAndParentCommentIdIsNull(postId, pageable);

        return GenerateResponse.generateSuccessResponse(
                200, "Get comment successfully !",
                new PageImpl<>(topLevelComments.getContent(),
                        pageable, topLevelComments.getTotalElements())
        );

    }

    @Override
    public ResponseEntity<?> TotalCommentsInPost(Long postId) {
        if (postId == null) {
            return GenerateResponse.generateErrorResponse(402, "post id is null !");
        }
        Integer totalComments = commentsRepository.countByPostId(postId);
        if (totalComments == 0) return GenerateResponse.generateErrorResponse
                (404, "comment not found !");

        return GenerateResponse.generateSuccessResponse(
                200, "get totalComments successfully !", totalComments
        );
    }
}
