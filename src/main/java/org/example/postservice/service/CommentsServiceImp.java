package org.example.postservice.service;



import org.example.postservice.model.dto.APIResponse;
import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.example.postservice.model.dto.ErrorResponse;
import org.example.postservice.model.entity.Comments;
import org.example.postservice.model.repository.CommentsRepository;
import org.example.postservice.utils.GenerateComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentsServiceImp implements ICommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public ResponseEntity<?> sentComments(CommentsRequest comments) {
        if (comments == null || comments.getPost_id() < 0 || comments.getUser_id() == null) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.NO_CONTENT.value(),
                            "Request invalid !"
                    )
            );
        }
        Comments comment = GenerateComments.generateComments(comments);
        commentsRepository.save(comment);

        return ResponseEntity.ok(
                new APIResponse<>(
                        HttpStatus.OK.value(),
                        "Comments sent",
                        "Comments success !"
                )
        );
    }

    @Override
    public ResponseEntity<?> getCommentsPost(Integer postId) {
        if (postId == null || postId < 0 || postId > commentsRepository.count()) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse(
                            HttpStatus.NO_CONTENT.value(),
                            "Invalid post id !"
                    )
            );
        }
        List<Comments> comments = commentsRepository.findByPostId(postId);

        return ResponseEntity.ok(
                new APIResponse<>(
                        HttpStatus.OK.value(),
                        "Comments sent",
                        comments
                )
        );
    }

    @Override
    public ResponseEntity<?> getCommentsUsers(UUID userId) {
        if (userId == null) {
            new ErrorResponse(
                    HttpStatus.NO_CONTENT.value(),
                    "Invalid user  id !"
            );
        }
        Comments comment = commentsRepository.findByUserId(userId);

        return ResponseEntity.ok(
                new APIResponse<>(
                        HttpStatus.OK.value(),
                        "Comments sent",
                        comment
                ));
    }
}
