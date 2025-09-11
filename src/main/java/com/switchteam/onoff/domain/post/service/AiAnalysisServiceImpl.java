package com.switchteam.onoff.domain.post.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchteam.onoff.global.config.OpenAiConfig;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class AiAnalysisServiceImpl implements AiAnalysisService {

    private final WebClient webClient;
    private final OpenAiConfig openAiConfig;

    public AiAnalysisServiceImpl(WebClient.Builder webClientBuilder, OpenAiConfig openAiConfig) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com").build();
        this.openAiConfig = openAiConfig;
    }

    @Override
    public String analyzePostContent(String content, List<MultipartFile> images) {
        try {
            // 1️⃣ 이미지 Base64 변환
            List<String> base64Images = new ArrayList<>();
            if (images != null) {
                for (MultipartFile img : images) {
                    String base64 = Base64.getEncoder().encodeToString(img.getBytes());
                    base64Images.add("data:image/jpeg;base64," + base64);
                }
            }

            // 2️⃣ 분석용 프롬프트 생성
            String promptText = createAnalysisPrompt(content, base64Images);

            // 3️⃣ content 배열 구성 (text + 각 이미지 separately)
            List<Map<String, Object>> contentList = new ArrayList<>();
            contentList.add(Map.of("type", "input_text", "text", promptText));

            for (String imageUrl : base64Images) {
                contentList.add(Map.of(
                        "type", "input_image",
                        "image_url", imageUrl
                ));
            }

            // 4️⃣ 요청 JSON 구성
            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-4o",
                    "input", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", contentList
                            )
                    )
            );

            // 5️⃣ WebClient 요청
            Mono<String> responseMono = webClient.post()
                    .uri("/v1/responses")
                    .header("Authorization", "Bearer " + openAiConfig.getOpenAiApiKey())
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class);

            String responseStr = responseMono.block();

            // 6️⃣ JSON 문자열만 추출 후 반환
            System.out.println("Extracted JSON:\n" + extractJsonStringFromResponse(responseStr));
            return extractJsonStringFromResponse(responseStr);

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.OPENAI_ANALYSIS_ERROR);
        }
    }

    /**
     * 분석용 프롬프트 생성 (강제 JSON 출력)
     */
    private String createAnalysisPrompt(String content, List<String> imageUrls) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("아래 게시글과 이미지를 분석하여 특징을 요약하고, 반드시 JSON 형태로 결과를 반환할 것.\n");
        prompt.append("게시글 내용:\n").append(content).append("\n");

        if (imageUrls != null && !imageUrls.isEmpty()) {
            prompt.append("첨부 이미지 수: ").append(imageUrls.size()).append("\n");
            prompt.append("이미지를 참고하여 인테리어, 분위기, 색감, 특이사항 등을 파악할 것.\n");
        }

        prompt.append("반드시 JSON만 반환할 것. JSON 외의 텍스트는 절대 포함하지 말 것.\n");
        prompt.append("결과 JSON 구조 및 예시는 다음과 같음:\n");
        prompt.append("""
        {
            "industryType": "예: 카페 70%, 주점 20%, 치킨집 10%",
            "businessScale": "예: 소규모, 중규모, 대규모",
            "targetCustomer": "예: 20~30대 젊은층",
            "uniqueFeatures": "예: 독특한 인테리어, 다양한 맥주 종류",
            "imageAnalysis": "예: 이미지에서 밝고 현대적인 인테리어 확인, 주점 분위기 강조"
        }
        """);
        prompt.append("레스토랑 같은 범위 말고 치킨집, 한식집, 중식집 처럼 좀더 카테고리를 세분화 할 것.\n");
        prompt.append("위 5개 키만 사용하고, 절대로 다른 키나 설명 텍스트를 추가하지 말 것.\n");
        prompt.append("각 항목을 최대한 구체적으로 작성할 것.\n");

        return prompt.toString();
    }

    /**
     * 모델 반환 문자열에서 JSON 부분만 추출하여 문자열로 반환
     */
    private String extractJsonStringFromResponse(String responseText) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseText);
            JsonNode outputArray = root.path("output");
            if (outputArray.isArray() && outputArray.size() > 0) {
                JsonNode firstMessage = outputArray.get(0);
                JsonNode contentArray = firstMessage.path("content");
                if (contentArray.isArray() && contentArray.size() > 0) {
                    String text = contentArray.get(0).path("text").asText();

                    // ```json ... ``` 제거
                    String cleaned = text.replaceAll("(?s)```json\\s*", "")
                            .replaceAll("(?s)```", "")
                            .trim();
                    return cleaned;
                }
            }
            return "{}"; // 실패 시 빈 JSON
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
