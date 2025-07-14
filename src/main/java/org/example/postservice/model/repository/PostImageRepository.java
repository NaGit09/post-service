package org.example.postservice.model.repository;

import org.example.postservice.model.entity.Post;
import org.example.postservice.model.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    void deletePostImageByPost(Post post);

    void deleteAllByPost(Post post);
}
