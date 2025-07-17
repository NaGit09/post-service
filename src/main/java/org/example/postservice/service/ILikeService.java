package org.example.postservice.service;

import org.example.postservice.model.dto.like.LikeRequest;
import org.example.postservice.model.dto.like.UnlikeRequest;

public interface ILikeService {
    Boolean like(LikeRequest request);

    Boolean unlike(UnlikeRequest request);

    Integer totalLikes(Long targetId, String typeLike);

}
