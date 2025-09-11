package com.switchteam.onoff.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostRequestDto {
    private String content;
    private Long propertyId;
    private List<MultipartFile> images = new ArrayList<>();
}
