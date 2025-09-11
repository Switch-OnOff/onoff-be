package com.switchteam.onoff.domain.post.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AiAnalysisService {

    String analyzePostContent(String content, List<MultipartFile> images);
}
