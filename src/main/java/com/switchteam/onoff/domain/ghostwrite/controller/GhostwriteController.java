package com.switchteam.onoff.domain.ghostwrite.controller;

import com.switchteam.onoff.domain.ghostwrite.dto.GhostwriteRequestDto;
import com.switchteam.onoff.domain.ghostwrite.service.GhostwriteService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/ghostwrite")
public class GhostwriteController {

    private final GhostwriteService ghostwriteService;

    @PostMapping("/generate")
    public ResponseEntity<CustomApiResponse<String>> generateGhostwrite(
            @RequestBody GhostwriteRequestDto dto
    ) {
        String content = ghostwriteService.generateContent(dto.getSentences());

        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.SENTENCE_GENERATE_SUCCESS, content
        ));
    }
}
