package org.example.postservice.model.repository;


import org.example.postservice.model.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {

    public Comments findByUserId(UUID userId);
    public List<Comments> findByPostId(Integer postId);


}