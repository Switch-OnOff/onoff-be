package com.switchteam.onoff.domain.post.service;

public interface PostImageService {

    /**
     * 게시글에 연결된 특정 이미지를 ID를 통해 삭제합니다. 해당 이미지는 게시글에서 제거되고
     * 관련 데이터베이스 레코드도 삭제됩니다. 이미지가 존재하지 않는 경우
     * 적절한 에러 코드와 함께 {@code CustomException}이 발생합니다.
     *
     * @param imageId 삭제할 이미지의 고유 식별자 (null이 아니어야 함)
     */
    void deletePostImage(Long imageId);
}
