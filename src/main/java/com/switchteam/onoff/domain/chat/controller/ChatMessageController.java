package com.switchteam.onoff.domain.chat.controller;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;
import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.dto.ChatMessageRequestDto;
import com.switchteam.onoff.domain.chat.dto.ChatMessageResponseDto;
import com.switchteam.onoff.domain.chat.service.ChatMessageService;
import com.switchteam.onoff.domain.chat.service.ChatMessageServiceImpl;
import com.switchteam.onoff.domain.chat.service.ChatRoomService;
import com.switchteam.onoff.domain.user.domain.User;
import com.switchteam.onoff.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessagingTemplate template;
    private final ChatMessageService chatMessageService; // 인터페이스 기준

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatMessageRequestDto dto) {
        // 서비스에서 저장 및 전송 처리
        ChatMessage savedMessage = chatMessageService.sendMessage(dto);

        ChatMessageResponseDto response = new ChatMessageResponseDto(
                savedMessage.getChatMessageId(),
                savedMessage.getRoomId().getRoomId(),
                savedMessage.getSenderId().getUserId(),
                savedMessage.getContent(),
                savedMessage.getSentAt()
        );

        // 구독자에게 전송
        template.convertAndSend("/topic/chat/" + response.getRoomId(), response);
    }
}