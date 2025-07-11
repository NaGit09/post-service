package org.example.postservice.model.dto.like;

import lombok.Data;

import java.util.UUID;

@Data
public class LikeRequest {
    UUID userId;
    Long targetId;
    String targetType;

}
