package org.example.postservice.service;

import org.example.postservice.constants.TypeLike;
import org.example.postservice.model.dto.like.LikeRequest;
import org.example.postservice.model.dto.like.UnlikeRequest;
import org.example.postservice.model.entity.Like;
import org.example.postservice.model.repository.LikeRepository;
import org.example.postservice.utils.GenerateLike;
import org.example.postservice.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LikeServiceImp implements ILikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Override
    public ResponseEntity<?> like(LikeRequest likeRequest) {
        UUID userId = likeRequest.getUserId();
        Long targetId = likeRequest.getTargetId();
        String typeLike = likeRequest.getTargetType();
        if (targetId == null || userId == null) {
            return GenerateResponse.generateErrorResponse(
                    401, "target id or user id is null");
        }

        try {
            TypeLike.valueOf(typeLike); // validate enum
            Like like = GenerateLike.generateLike(userId, targetId, typeLike);
            likeRepository.save(like);
            return GenerateResponse.generateSuccessResponse(
                    200, "like successfully", like.getId());
        } catch (IllegalArgumentException e) {
            return GenerateResponse.generateErrorResponse(
                    401, "typeLike invalid");
        }

    }


    @Override
    public ResponseEntity<?> unlike(UnlikeRequest unlikeRequest) {
        UUID userId = unlikeRequest.getUserId();
        Long targetId = unlikeRequest.getTargetId();
        String typeLike = unlikeRequest.getTargetType();
        Long id = unlikeRequest.getId();

        if (targetId == null || userId == null) {
            return GenerateResponse.generateErrorResponse(
                    401, "target id or user id is null");
        }

        try {
            TypeLike.valueOf(typeLike); // validate enum
            Like like = likeRepository.findById(id).orElse(null);
            if (like == null) {
                return GenerateResponse.generateErrorResponse(
                        404, "target id not found"
                );
            }
            likeRepository.delete(like);
            return GenerateResponse.generateSuccessResponse(
                    200, "unlike successfully", like.getId()
            );
        } catch (IllegalArgumentException e) {
            return GenerateResponse.generateErrorResponse(
                    401, "typeLike invalid");
        }
    }

    @Override
    public ResponseEntity<?> totalLikes(Long targetId, String typeLike) {
        Integer totalLike = likeRepository.countLikesByTargetIdAndTargetType(targetId, typeLike);
        if (totalLike == 0) {
            return GenerateResponse.generateErrorResponse(
                    404, "target id not found"
            );
        }

        return GenerateResponse.generateSuccessResponse(
                200, "total likes successfully", totalLike
        );
    }
}
