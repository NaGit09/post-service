package org.example.postservice.utils;

import org.example.postservice.model.entity.Like;

import java.util.UUID;

public class GenerateLike {
    public  static Like generateLike (UUID userId , Long targetId , String typeLike ) {
        return Like.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(typeLike)
                .build();
    }
}
