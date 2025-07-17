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
    public Boolean like(LikeRequest likeRequest) {
        UUID userId = likeRequest.getUserId();
        Long targetId = likeRequest.getTargetId();
        String typeLike = likeRequest.getTargetType();

        if (targetId == null || userId == null) {
            return false;
        }

        try {
            TypeLike.valueOf(typeLike);
            Like like = GenerateLike.generateLike(userId, targetId, typeLike);
            likeRepository.save(like);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    @Override
    public Boolean unlike(UnlikeRequest unlikeRequest) {
        UUID userId = unlikeRequest.getUserId();
        Long targetId = unlikeRequest.getTargetId();
        String typeLike = unlikeRequest.getTargetType();
        Long id = unlikeRequest.getId();

        if (targetId == null || userId == null) {
            return false;
        }

        try {
            TypeLike.valueOf(typeLike); // validate enum
            Like like = likeRepository.findById(id).orElse(null);
            if (like == null) {
                return false;
            }
            likeRepository.delete(like);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Integer totalLikes(Long targetId, String typeLike) {
        return likeRepository.countLikesByTargetIdAndTargetType(targetId, typeLike);
    }
}
