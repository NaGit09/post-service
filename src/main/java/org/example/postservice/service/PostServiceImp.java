package org.example.postservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.postservice.config.UserClient;
import org.example.postservice.model.dto.orther.UserInforResponse;
import org.example.postservice.model.dto.post.EditPost;
import org.example.postservice.model.dto.post.PostDTO;
import org.example.postservice.model.dto.post.PostImageRequest;
import org.example.postservice.model.dto.post.PostRequest;

import org.example.postservice.model.entity.Post;
import org.example.postservice.model.entity.PostImage;
import org.example.postservice.model.repository.PostImageRepository;
import org.example.postservice.model.repository.PostRepository;

import org.example.postservice.utils.GeneratePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements IPostService {
    @Autowired
    public final PostRepository postRepository;
    @Autowired
    public PostImageRepository postImageRepository;
    @Autowired
    public UserClient userClient;

    @Override
    public PostDTO CreatePost(PostRequest postRequest) {
        Post post = GeneratePost.generatePost(postRequest);
        List<PostImageRequest> postImages = postRequest.getPostRequestImages();


        if (postImages != null && !postImages.isEmpty()) {
            List<PostImage> images = postImages.stream()
                    .map(req -> PostImage.builder()
                            .url(req.getUrl())
                            .publicId(req.getPublicId())
                            .post(post)
                            .build())
                    .toList();

            postRepository.save(post);
            postImageRepository.saveAll(images);
        }

        Post postReturn = postRepository.findById(post.getId()).orElse(null);
        if (postReturn == null) {
            return null;
        }
        return GeneratePost.generatePostDTO(postReturn, null);
    }

    @Override
    public Boolean UpdatePost(EditPost editPost) {
        Long postId = editPost.getPostId();
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false;
        }


        postImageRepository.deleteAllByPost(post);

        List<PostImageRequest> imageRequests = editPost.getPostImageList();
        if (imageRequests != null && !imageRequests.isEmpty()) {
            List<PostImage> newImages = imageRequests.stream()
                    .map(req -> PostImage.builder()
                            .url(req.getUrl())
                            .publicId(req.getPublicId())
                            .post(post)
                            .build())
                    .toList();
            postImageRepository.saveAll(newImages);
        }

        postRepository.save(GeneratePost.generatePostUpdate(post, editPost));

        return true;
    }

    @Override
    @Transactional
    public Boolean DeletePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return false;
        }

        postImageRepository.deleteAllByPost(post);


        postRepository.delete(post);

        return true;
    }

    @Override
    public PostDTO GetPost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return null;
        }
        UUID userId = post.getUserId();
        if (userId == null) {
            return null;
        }
        UserInforResponse userDto = userClient.getUserById(userId);
        return GeneratePost.generatePostDTO(post, userDto);

    }

    @Override
    public List<PostDTO> ListPostRandom(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public List<PostDTO> ListPostsUser(UUID userId, Integer page, Integer pageSize) {
        return null;
    }


}
