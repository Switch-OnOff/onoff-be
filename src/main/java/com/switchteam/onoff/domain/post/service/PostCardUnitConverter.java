package com.switchteam.onoff.domain.post.service;

import com.switchteam.onoff.domain.post.domain.Post;
import com.switchteam.onoff.domain.post.domain.PostImage;
import com.switchteam.onoff.domain.post.dto.PostCardListResponseDto;

public class PostCardUnitConverter {

    public static PostCardListResponseDto.PostCardUnitResponseDto toDto(Post post) {
        PostCardListResponseDto.PostCardUnitResponseDto dto = new PostCardListResponseDto.PostCardUnitResponseDto();
        dto.setPropertyId(post.getProperty() != null ? post.getProperty().getId() : null);
        dto.setOwnerId(post.getUser() != null ? post.getUser().getUserId() : null);

        if (post.getImages() != null && !post.getImages().isEmpty()) {
            PostImage img = post.getImages().get(0);
            dto.setRepresentativeImage(img.getImageData());
        }

        return dto;
    }

}
