package com.switchteam.onoff.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AiAnalysisServiceImpl implements AiAnalysisService {

    @Override
    public String analyzePostContent(String content, List<MultipartFile> images) {
        return "";
    }
}
