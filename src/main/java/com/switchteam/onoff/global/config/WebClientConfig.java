package com.switchteam.onoff.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {


    @Bean
    public WebClient kakaoWebClient(
            @Value("${kakao.api.key}")
            String apiKey
    ) {
        return WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + apiKey)
                .build();
    }
}
