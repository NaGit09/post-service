package org.example.postservice.service;

import org.example.postservice.model.dto.post.PostRequest;
import org.example.postservice.model.entity.Post;
import org.example.postservice.model.repository.PostRepository;
import org.example.postservice.utils.GeneratePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImp {
    @Autowired
    private PostRepository postRepository;
    public boolean CreatePost(PostRequest post) {
        if (post.getUserId() == null) {
            return false;
        }
        Post p = GeneratePost.generatePost(post);
        postRepository.save(p);
        return true;
    }
    public Optional<Post> getPost (Integer id) {
        return  postRepository.findById(id);
    }
    public List<Post> getUserPosts (UUID user_id) {
        return postRepository.findByUserId(user_id);
    }
}
