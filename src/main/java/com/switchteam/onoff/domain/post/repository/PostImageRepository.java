package com.switchteam.onoff.domain.post.repository;

import com.switchteam.onoff.domain.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPost_PostId(Long postId);
}
