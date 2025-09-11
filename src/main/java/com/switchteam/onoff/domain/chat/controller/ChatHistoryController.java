package com.switchteam.onoff.domain.chat.controller;

import com.switchteam.onoff.domain.chat.dto.ChatRoomMessageResponseDto;
import com.switchteam.onoff.domain.chat.service.ChatHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/")
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    @GetMapping("/room/{roomId}/messages")
    public ChatRoomMessageResponseDto getChatHistory(@PathVariable Long roomId) {
        return chatHistoryService.getChatHistory(roomId);
    }
}
