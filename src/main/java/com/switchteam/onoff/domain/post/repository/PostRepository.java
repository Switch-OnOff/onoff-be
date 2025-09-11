package com.switchteam.onoff.domain.post.repository;

import com.switchteam.onoff.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser_UserId(Long userId);
    Optional<Post> findPostByPropertyId(Long propertyId);
}
