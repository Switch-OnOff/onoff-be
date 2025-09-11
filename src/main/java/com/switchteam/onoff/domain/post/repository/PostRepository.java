package com.switchteam.onoff.domain.post.repository;

import com.switchteam.onoff.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser_UserId(Long userId);
}
