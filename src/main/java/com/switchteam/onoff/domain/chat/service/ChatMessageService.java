package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    ChatMessage saveMessage(ChatMessage message);

    List<ChatMessage> getMessagesByRoomId(Long roomId);
}
