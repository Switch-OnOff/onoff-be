package com.switchteam.onoff.domain.post.service;

import com.switchteam.onoff.domain.post.domain.PostImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostImageConverterService {

    List<PostImage> convertToPostImages(List<MultipartFile> files);
}
