package com.switchteam.onoff.domain.post.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchteam.onoff.domain.post.domain.Post;
import com.switchteam.onoff.domain.post.domain.PostImage;
import com.switchteam.onoff.domain.post.dto.PostCardListResponseDto;

public class PostCardUnitConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static PostCardListResponseDto.PostCardUnitResponseDto toDto(Post post) {
        PostCardListResponseDto.PostCardUnitResponseDto dto = new PostCardListResponseDto.PostCardUnitResponseDto();
        dto.setPropertyId(post.getProperty() != null ? post.getProperty().getId() : null);
        dto.setOwnerId(post.getUser() != null ? post.getUser().getUserId() : null);

        if (post.getImages() != null && !post.getImages().isEmpty()) {
            PostImage img = post.getImages().get(0);
            dto.setRepresentativeImage(img.getImageData());
        }

        // analysisResult JSON -> 객체 변환
        if (post.getAnalysisResult() != null) {
            try {
                PostCardListResponseDto.PostCardUnitResponseDto.AnalysisResult parsed =
                        objectMapper.readValue(post.getAnalysisResult(),
                                PostCardListResponseDto.PostCardUnitResponseDto.AnalysisResult.class);
                dto.setAnalysisResult(parsed);
            } catch (Exception e) {
                // 변환 실패하면 그냥 null 세팅
                dto.setAnalysisResult(null);
            }
        }

        return dto;
    }
}
