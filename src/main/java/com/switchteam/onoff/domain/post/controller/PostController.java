package com.switchteam.onoff.domain.post.controller;

import com.switchteam.onoff.domain.post.domain.Post;
import com.switchteam.onoff.domain.post.dto.PostRequestDto;
import com.switchteam.onoff.domain.post.service.PostImageService;
import com.switchteam.onoff.domain.post.service.PostService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostImageService postImageService;

    @PostMapping("/{userId}")
    public ResponseEntity<CustomApiResponse<Post>> createPost(
            @PathVariable Long userId,
            @ModelAttribute PostRequestDto postRequestDto
    ) {
        Post post = postService.createPostWithImages(
                userId,
                postRequestDto.getContent(),
                postRequestDto.getImages()
        );

        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.POST_CREATION_SUCCESS, post)
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<CustomApiResponse<Void>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.POST_DELETION_SUCCESS)
        );
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<CustomApiResponse<Void>> deletePostImage(@PathVariable Long imageId) {
        postImageService.deletePostImage(imageId);
        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.POST_IMAGE_DELETION_SUCCESS)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomApiResponse<List<Post>>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.POSTS_FETCH_SUCCESS, posts)
        );
    }
}
