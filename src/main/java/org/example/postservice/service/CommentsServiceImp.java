package org.example.postservice.service;


import org.example.postservice.model.dto.commnet.CommentDTO;
import org.example.postservice.model.dto.commnet.CommentEditRequest;
import org.example.postservice.model.dto.commnet.CommentRoot;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.entity.Comments;
import org.example.postservice.model.repository.CommentsRepository;
import org.example.postservice.utils.GenerateComments;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentsServiceImp implements ICommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public Long CreateComment(CommentsRequest commentsRequest) {
        Comments comments = GenerateComments.generateComments(commentsRequest);
        commentsRepository.save(comments);
        return  comments.getId();
    }

    @Override
    public Boolean DeleteComment(Long commentId) {
        if (commentId == null) {
            return false;
        }
        Comments comments = commentsRepository.findById(commentId).orElse(null);
        if (comments == null) {
            return false;
        }
        commentsRepository.deleteById(commentId);
        return true;
    }

    @Override
    public Boolean UpdateComment(CommentEditRequest commentsEditRequest) {
        Comments comment = commentsRepository.findById(commentsEditRequest.getId()).orElse(null);
        if (comment == null ) {
            return false;
        }
        Comments newComment = GenerateComments.updateComment(comment, commentsEditRequest);
        commentsRepository.save(newComment);
        return true;
    }

    @Override
    public Page<CommentRoot> getTopLevelComments(Long postId, int page, int size) {
        // Kiểm tra nếu postId là null
        if (postId == null) {
            return  null;
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
        return  new PageImpl<>(commentDTOs, pageable, topLevelComments.getTotalElements());
    }


    @Override
    public Page<CommentDTO> getRepliesComments(Long commentId, int page, int size) {
        // Lấy comment gốc từ repository
        Comments comments = commentsRepository.findById(commentId).orElse(null);

        // Kiểm tra nếu comment không tồn tại
        if (comments == null) {
            return null;
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
        return new PageImpl<>(result, pageable, repliesComments.getTotalElements());
    }

    @Override
    public Integer TotalCommentsInPost(Long postId) {
        if (postId == null) {
            return -1;
        }
        Integer totalComments = commentsRepository.countByPostId(postId);
        if (totalComments == 0) return -1;

        return totalComments;

    }

    @Override
    public Integer TotalCommentReplies(Long commentId) {
        if (commentId == null) {
            return -1;
        }

        Comments comments = commentsRepository.findById(commentId).orElse(null);

        if (comments == null) {
            return -1;
        }
        Long parentComment = comments.getParentCommentId() == null
                ? comments.getId() : comments.getParentCommentId();

        return commentsRepository.countByParentCommentId(parentComment);
    }
}
