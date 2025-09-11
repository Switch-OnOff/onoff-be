package com.switchteam.onoff.domain.post.service;

import com.switchteam.onoff.domain.post.domain.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    /**
     * 사용자의 게시글을 생성합니다. 게시글에는 텍스트 내용과 선택적으로 이미지가 포함될 수 있습니다.
     * 게시글 내용은 추가적인 메타데이터를 위해 분석될 수 있습니다.
     *
     * @param userId  게시글을 작성하는 사용자의 ID (null이 아니어야 함)
     * @param content 게시글의 텍스트 내용 (null이거나 비어있으면 안됨)
     * @param images  게시글에 첨부할 이미지 파일 목록 (null이거나 비어있을 수 있음)
     * @return 새로 생성된 게시글 객체
     */
    Post createPostWithImages(Long userId, Long propertyId, String content, List<MultipartFile> images);

    /**
     * ID로 지정된 게시글을 삭제합니다.
     *
     * @param postId 삭제할 게시글의 ID (null이 아니어야 함)
     */
    void deletePost(Long postId);

    /**
     * 특정 사용자가 작성한 게시글 목록을 조회합니다.
     *
     * @param userId 게시글을 조회할 사용자의 ID (null이 아니어야 함)
     * @return 해당 사용자가 작성한 게시글 목록
     */
    List<Post> getPostsByUserId(Long userId);

    /**
     * 모든 게시글을 조회합니다.
     *
     * @return 모든 게시글 목록
     */
    List<Post> getAllPosts();


    /**
     * Retrieves a post associated with a given property ID.
     *
     * @param propertyId the ID of the property associated with the post (must not be null)
     * @return the post associated with the given property ID, or null if no such post exists
     */
    Post getPostByPropertyId(Long propertyId);
}