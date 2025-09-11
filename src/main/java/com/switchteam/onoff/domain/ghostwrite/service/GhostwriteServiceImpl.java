package com.switchteam.onoff.domain.ghostwrite.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchteam.onoff.global.config.OpenAiConfig;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GhostwriteServiceImpl implements GhostwriteService {

    private final WebClient.Builder webClientBuilder;
    private final OpenAiConfig openAiConfig;

    @Override
    public String generateContent(List<String> sentences) {
        try {
            String prompt = createPrompt(sentences);

            Map<String, Object> requestBody = Map.of(
                    "model", openAiConfig.getOpenAiApiModel(),
                    "messages", List.of(
                            Map.of("role", "system", "content", "너는 글쓰기 전문가야."),
                            Map.of("role", "user", "content", prompt)
                    ),
                    "temperature", 0.7
            );

            // Map 그대로 bodyValue로 전달 → JSON 직렬화 문제 방지
            String response = webClientBuilder.build()
                    .post()
                    .uri(openAiConfig.getOpenAiApiUrl())
                    .header("Authorization", "Bearer " + openAiConfig.getOpenAiApiKey())
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractTextFromResponse(response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.AI_GENERATE_ERROR);
        }
    }

    private String extractTextFromResponse(String responseText) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseText);
            JsonNode choices = root.path("choices");
            if (choices.isArray() && !choices.isEmpty()) {
                return choices.get(0).path("message").path("content").asText().trim();
            }
            return "AI 결과를 파싱하지 못했습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "AI 결과 처리 오류";
        }
    }

    private String createPrompt(List<String> sentences) {
        StringBuilder prompt = new StringBuilder("다음 문장들을 자연스럽게 이어서 하나의 글로 작성해줘:\n");
        for (String sentence : sentences) {
            prompt.append("- ").append(sentence).append("\n");
        }
        prompt.append("글의 길이는 200자 이상으로 해줘. 반환 값은 String 형태로만 반환할 것.");
        return prompt.toString();
    }
}
