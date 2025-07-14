package org.example.postservice.model.repository;


import org.example.postservice.model.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {

    Integer countByPostId(Long postId);

    // get top comment
    Page<Comments> findByPostIdAndParentCommentIdIsNull(Long postId, Pageable pageable);

    Page<Comments> findByParentCommentId(Long parentCommentId , Pageable pageable);

    void deleteById(Long id);

    Page<Comments> findById(Long parentCommentId, Pageable pageable);
}