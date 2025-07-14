package org.example.postservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.postservice.model.dto.post.EditPost;
import org.example.postservice.model.dto.post.PostImageRequest;
import org.example.postservice.model.dto.post.PostRequest;

import org.example.postservice.model.entity.Post;
import org.example.postservice.model.entity.PostImage;
import org.example.postservice.model.repository.PostImageRepository;
import org.example.postservice.model.repository.PostRepository;

import org.example.postservice.utils.GenerateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements IPostService {

    public final PostRepository postRepository;

    public PostImageRepository postImageRepository;

    @Override
    public ResponseEntity<?> CreatePost(PostRequest postRequest) {
//        destructuring
        UUID userId = postRequest.getUserId();
        String content = postRequest.getContent();
        String modes = postRequest.getModes();
        Boolean isComment = postRequest.getIsComment();
        Boolean isShare = postRequest.getIsShare();
        Boolean isSaved = postRequest.getIsSaved();
        Boolean isLiked = postRequest.getIsLiked();
        List<PostImageRequest> postImages = postRequest.getPostRequestImages();

        if (userId == null) {
            return GenerateResponse.generateErrorResponse(
                    401, "User Id can't be null");
        }
        Post post = postRepository.save(Post.builder()
                .userId(userId)
                .content(content)
                .isComment(isComment)
                .isShare(isShare)
                .isSaved(isSaved)
                .modes(modes)
                .isLike(isLiked)
                .build());

        if (postImages != null && !postImages.isEmpty()) {
            List<PostImage> images = postImages.stream()
                    .map(req -> PostImage.builder()
                            .url(req.getUrl())
                            .publicId(req.getPublicId())
                            .post(post)
                            .build())
                    .toList();

            postImageRepository.saveAll(images);
        }

        Optional<Post> postReturn = postRepository.findById(post.getId());
        if (postReturn.isEmpty()) {
            return GenerateResponse.generateErrorResponse(401, "Post not found");
        }
        return GenerateResponse.generateSuccessResponse(
                200, "create post successfully !", postReturn.get());
    }


    @Override
    public ResponseEntity<?> UpdatePost(EditPost editPost) {
        Long postId = editPost.getPostId();
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) {
            return GenerateResponse.generateErrorResponse(404, "Post not found");
        }

        Post post = optionalPost.get();

        // Cập nhật các trường cơ bản
        post.setContent(editPost.getContent());
        post.setIsComment(editPost.getIsComment());
        post.setIsLike(editPost.getIsLike());
        post.setIsShare(editPost.getIsShare());
        post.setModes(editPost.getMode());
        post.setIsSaved(editPost.getIsSaved());

        // Cập nhật lại ảnh: xóa hết ảnh cũ
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

        postRepository.save(post);

        return GenerateResponse.generateSuccessResponse(
                200, "Update post successfully!", post
        );
    }


    @Override
    @Transactional
    public ResponseEntity<?> DeletePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return GenerateResponse.generateErrorResponse(404, "Post not found");
        }

        postImageRepository.deleteAllByPost(post);


        postRepository.delete(post);

        return GenerateResponse.generateSuccessResponse(
                200, "Delete post successfully", true
        );
    }


    @Override
    public ResponseEntity<?> GetPost(Long postId) {
        return null;
    }

    @Override
    public ResponseEntity<?> ListPostRandom(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<?> ListPostsUser(UUID userId, Integer page, Integer pageSize) {
        return null;
    }


}
