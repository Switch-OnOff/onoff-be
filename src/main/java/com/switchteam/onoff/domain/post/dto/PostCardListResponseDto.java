package com.switchteam.onoff.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostCardListResponseDto {

    private List<PostCardUnitResponseDto> postCards;

    @Getter
    @Setter
    public static class PostCardUnitResponseDto {
        private Long propertyId;
        private Long ownerId;
        private byte[] representativeImage;
        private AnalysisResult analysisResult;

        @Getter
        @Setter
        public static class AnalysisResult {
            private String industryType;
            private String businessScale;
            private String targetCustomer;
            private String uniqueFeatures;
            private String imageAnalysis;
        }
    }

}
