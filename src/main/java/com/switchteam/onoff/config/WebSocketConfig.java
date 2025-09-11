package com.switchteam.onoff.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 메시지 브로커를 설정하는 구성 클래스입니다.
 * <p>
 * 이 클래스는 STOMP 프로토콜을 사용한 WebSocket 지원을 활성화하고,
 * 클라이언트가 연결할 엔드포인트와 메시지 브로커의 경로를 설정합니다.
 * </p>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * WebSocket 통신을 위한 STOMP 엔드포인트를 등록합니다.
     *
     * @param registry STOMP 엔드포인트를 위한 레지스트리
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * WebSocket 통신을 위한 메시지 브로커 설정을 구성합니다.
     *
     * @param registry 메시지 브로커 옵션을 정의하는데 사용되는 {@link MessageBrokerRegistry}
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
