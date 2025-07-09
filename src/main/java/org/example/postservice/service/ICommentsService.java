package org.example.postservice.service;


import org.example.postservice.model.dto.commnet.CommentsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface ICommentsService {
     ResponseEntity<?> sentComments (@RequestBody CommentsRequest comments);
     ResponseEntity<?> getCommentsPost (@RequestParam Integer postId);
     ResponseEntity<?> getCommentsUsers (@RequestParam UUID userId);

}