package com.switchteam.onoff.domain.post.service;

import com.switchteam.onoff.domain.post.domain.Post;
import com.switchteam.onoff.domain.post.domain.PostImage;
import com.switchteam.onoff.domain.post.repository.PostImageRepository;
import com.switchteam.onoff.domain.post.repository.PostRepository;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void deletePostImage(Long imageId) {
        PostImage image = postImageRepository.findById(imageId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_IMAGE_NOT_FOUND));

        Post post = image.getPost();
        post.removeImage(image);
    }
}
