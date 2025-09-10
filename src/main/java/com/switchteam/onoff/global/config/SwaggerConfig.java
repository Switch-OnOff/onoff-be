package com.switchteam.onoff.global.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private final String API_NAME = "SwitchOnOff";
    private final String API_VERSION = "1.0.0";
    private final String API_DESCRIPTION = "CardGGaduekMainService API 명세서";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title(API_NAME) // API의 제목
                .description(API_DESCRIPTION) // API에 대한 설명
                .version(API_VERSION); // API의 버전
    }
}
