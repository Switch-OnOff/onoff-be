package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.dto.ChatRoomMessageResponseDto;

public interface ChatHistoryService {

    ChatRoomMessageResponseDto getChatHistory(Long roomId);
}
