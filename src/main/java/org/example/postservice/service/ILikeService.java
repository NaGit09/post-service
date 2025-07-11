package org.example.postservice.service;

import org.example.postservice.model.dto.like.LikeRequest;
import org.example.postservice.model.dto.like.UnlikeRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ILikeService {
    ResponseEntity<?> like(LikeRequest request);
    ResponseEntity<?> unlike(UnlikeRequest request);
    ResponseEntity<?> totalLikes(Long targetId , String typeLike);


}
