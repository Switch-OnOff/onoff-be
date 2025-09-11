package com.switchteam.onoff.domain.post.controller;

import com.switchteam.onoff.domain.post.domain.Post;
import com.switchteam.onoff.domain.post.dto.PostCardListResponseDto;
import com.switchteam.onoff.domain.post.dto.PostRequestDto;
import com.switchteam.onoff.domain.post.service.PostCardUnitConverter;
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
                postRequestDto.getPropertyId(),
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

    @GetMapping("/card_list")
    public ResponseEntity<CustomApiResponse<List<PostCardListResponseDto.PostCardUnitResponseDto>>> getPostCardList() {
        // 모든 Post 조회
        List<Post> posts = postService.getAllPosts();

        // Post → PostCardUnitResponseDto 변환
        List<PostCardListResponseDto.PostCardUnitResponseDto> postCardList =
                posts.stream()
                        .map(PostCardUnitConverter::toDto)
                        .toList();

        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.POSTS_FETCH_SUCCESS, postCardList)
        );
    }

    @GetMapping("/card/{propertyId}")
    public ResponseEntity<CustomApiResponse<PostCardListResponseDto.PostCardUnitResponseDto>> getPostCardByProperty(
            @PathVariable Long propertyId
    ) {
        // Property 기준 Post 조회 (단일)
        Post post = postService.getPostByPropertyId(propertyId);

        // DTO 변환
        PostCardListResponseDto.PostCardUnitResponseDto dto = PostCardUnitConverter.toDto(post);

        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.POSTS_FETCH_SUCCESS, dto)
        );
    }
}
