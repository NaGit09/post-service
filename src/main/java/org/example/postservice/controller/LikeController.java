package org.example.postservice.controller;

import org.example.postservice.model.dto.like.LikeRequest;
import org.example.postservice.model.dto.like.UnlikeRequest;
import org.example.postservice.service.LikeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-service/like")
public class LikeController {
    @Autowired
    private LikeServiceImp likeServiceImp;
    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestBody LikeRequest likeRequest) {
        return likeServiceImp.like(likeRequest);
    }
    @PostMapping("/unlike")
    public ResponseEntity<?> like(@RequestBody UnlikeRequest unlikeRequest) {
        return likeServiceImp.unlike(unlikeRequest);
    }
    @GetMapping("/totalLike/{targetId}/{targetType}")
    ResponseEntity<?> totalLikes(@PathVariable Long targetId, @PathVariable String targetType) {
        return likeServiceImp.totalLikes(targetId, targetType);
    }

}
