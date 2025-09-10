package com.switchteam.onoff.domain.chat.controller;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;
import com.switchteam.onoff.domain.chat.service.ChatMessageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessagingTemplate template;
    private final ChatMessageServiceImpl chatMessageService;

    /**
     * 채팅 메시지 전송을 처리합니다. 제공된 메시지를 데이터베이스에 저장하고
     * 해당 채팅방의 모든 구독자에게 브로드캐스트합니다.
     *
     * @param message 전송될 채팅 메시지. 발신자 정보, 메시지 내용, 소속 채팅방 등의 정보를 포함합니다.
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage message) {
        // 1. DB 저장
        ChatMessage savedMessage = chatMessageService.saveMessage(message);

        // 2. 구독자에게 전송
        template.convertAndSend("/topic/chat/" + savedMessage.getRoom().getRoomId(), savedMessage);
    }
}