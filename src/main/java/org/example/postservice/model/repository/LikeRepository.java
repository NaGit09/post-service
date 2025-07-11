package org.example.postservice.model.repository;

import org.example.postservice.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Integer countLikesByTargetIdAndTargetType(Long targetId, String targetType);
}
