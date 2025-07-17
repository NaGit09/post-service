package org.example.postservice.controller;

import org.example.postservice.model.dto.like.LikeRequest;
import org.example.postservice.model.dto.like.UnlikeRequest;
import org.example.postservice.service.LikeServiceImp;
import org.example.postservice.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-service/like")
public class LikeController {
    @Autowired
    private LikeServiceImp likeServiceImp;

    @PostMapping("/like")
    public ResponseEntity<?> like
            (@RequestBody LikeRequest likeRequest) {
        Boolean result = likeServiceImp.like(likeRequest);
        if (!result) {
            return GenerateResponse.generateErrorResponse
                    (401, "like failed");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "like successfully !", true);
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> unlike
            (@RequestBody UnlikeRequest unlikeRequest) {
        Boolean result = likeServiceImp.unlike(unlikeRequest);
        if (!result) {
            return GenerateResponse.generateErrorResponse
                    (401, "unlike failed");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "unlike successfully !", true);
    }

    @GetMapping("/total/{targetId}/{targetType}")
    ResponseEntity<?> totalLikes
            (@PathVariable Long targetId,
             @PathVariable String targetType) {
        Integer result = likeServiceImp.totalLikes(targetId, targetType);
        if (result == 0) {
            return GenerateResponse.generateErrorResponse
                    (401, "get total like failed");
        }
        return GenerateResponse.generateSuccessResponse
                (200, "unlike successfully !", result);

    }

}
