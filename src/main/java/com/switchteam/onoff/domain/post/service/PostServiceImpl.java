package com.switchteam.onoff.domain.post.service;

import com.switchteam.onoff.domain.post.domain.Post;
import com.switchteam.onoff.domain.post.domain.PostImage;
import com.switchteam.onoff.domain.post.repository.PostRepository;
import com.switchteam.onoff.domain.property.entity.Property;
import com.switchteam.onoff.domain.property.service.PropertyService;
import com.switchteam.onoff.domain.user.domain.User;
import com.switchteam.onoff.domain.user.repository.UserRepository;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AiAnalysisService aiAnalysisService;
    private final PostImageConverterService postImageConverterService;
    private final PropertyService propertyService;

    @Override
    @Transactional
    public Post createPostWithImages(Long userId, Long propertyId, String content, List<MultipartFile> images) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Property property = propertyService.getPropertyById(propertyId);

        // AI 분석
        String analysisResult = aiAnalysisService.analyzePostContent(content, images);

        // 이미지 변환
        List<PostImage> imageEntities = postImageConverterService.convertToPostImages(images);

        Post post = Post.builder()
                .user(user)
                .property(property)
                .content(content)
                .analysisResult(analysisResult)
                .build();

        if (post.getImages() == null) {
            post.setImages(new ArrayList<>());
        }

        if (images != null) {
            for (PostImage image : imageEntities) {
                post.addImage(image);
            }
        }

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostByPropertyId(Long propertyId) {
        return postRepository.findPostByPropertyId(propertyId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_IMAGE_NOT_FOUND));
    }
}
