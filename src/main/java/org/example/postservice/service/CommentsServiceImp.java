package org.example.postservice.service;

import org.example.postservice.model.dto.commnet.CommentDTO;
import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentRoot;
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
    public ResponseEntity<?> getTopLevelComments(Long postId, int page, int size) {
        // Kiểm tra nếu postId là null
        if (postId == null) {
            return GenerateResponse.generateErrorResponse(402, "Post ID is null!");
        }

        // Phân trang cho các comment gốc
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // Truy vấn các comment gốc của bài viết (comment có parentCommentId = null)
        Page<Comments> topLevelComments = commentsRepository.
                findByPostIdAndParentCommentIdIsNull(postId, pageable);

        // Chuyển đổi các comment gốc thành DTO
        List<CommentRoot> commentDTOs = topLevelComments.getContent().stream()
                .map(GenerateComments::generateCommentRoot)
                .collect(Collectors.toList());

        // Trả về kết quả phân trang
        return GenerateResponse.generateSuccessResponse(
                200, "Get top-level comments successfully!",
                new PageImpl<>(commentDTOs, pageable, topLevelComments.getTotalElements())
        );
    }


    @Override
    public ResponseEntity<?> getRepliesComments(Long commentId, int page, int size) {
        // Lấy comment gốc từ repository
        Comments comments = commentsRepository.findById(commentId).orElse(null);

        // Kiểm tra nếu comment không tồn tại
        if (comments == null) {
            return GenerateResponse.generateErrorResponse(404, "Comment not found!");
        }

        // Phân trang
        Pageable pageable = PageRequest.of(page, size);

        // Kiểm tra nếu comment là comment gốc hoặc comment con
        Long parentCommentId = comments.getParentCommentId() == null
                ? comments.getId() : comments.getParentCommentId();

        // Tìm các comment con của comment gốc hoặc comment cha
        Page<Comments> repliesComments = commentsRepository.
                findByParentCommentId(parentCommentId, pageable);

        // Chuyển đổi thành DTO
        List<CommentDTO> result = repliesComments.stream()
                .map(GenerateComments::generateCommentsDto)
                .collect(Collectors.toList());

        // Trả về kết quả
        return GenerateResponse.generateSuccessResponse(
                200, "Get replies comments successfully!",
                new PageImpl<>(result, pageable, repliesComments.getTotalElements())
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

    @Override
    public ResponseEntity<?> TotalCommentReplies(Long commentId) {
        if (commentId == null) {
            return GenerateResponse.generateErrorResponse
                    (402, "comment id is null !");
        }

        Comments comments = commentsRepository.findById(commentId).orElse(null);

        if (comments == null) {
            return GenerateResponse.generateErrorResponse
                    (404, "comment not found !");
        }
        Long parentComment = comments.getParentCommentId() == null
                ? comments.getId() : comments.getParentCommentId();

        Integer totalReplies = commentsRepository.countByParentCommentId(parentComment);

        return GenerateResponse.generateSuccessResponse(
                200, "get totalReplies successfully !", totalReplies
        );
    }
}
