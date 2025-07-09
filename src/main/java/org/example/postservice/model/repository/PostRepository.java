package org.example.postservice.model.repository;

import org.example.postservice.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRepository  extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(UUID userId);
}
