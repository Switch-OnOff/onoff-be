package com.switchteam.onoff.domain.post.service;

import com.switchteam.onoff.domain.post.domain.PostImage;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostImageConverterServiceImpl implements PostImageConverterService {

    @Override
    public List<PostImage> convertToPostImages(List<MultipartFile> files) {
        List<PostImage> imageEntities = new ArrayList<>();

        if (files != null) {
            try {
                for (MultipartFile file : files) {
                    PostImage image = PostImage.builder()
                            .imageData(file.getBytes())
                            .originalName(file.getOriginalFilename())
                            .fileSize(file.getSize())
                            .contentType(file.getContentType())
                            .build();

                    imageEntities.add(image);
                }
            } catch (IOException e) {
                throw new CustomException(ErrorCode.IMAGE_CONVERSION_FAILED);
            }
        }
        return imageEntities;
    }
}
