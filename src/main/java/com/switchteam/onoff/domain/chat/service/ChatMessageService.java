package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {

    ChatMessage saveMessage(ChatMessage message);

    List<ChatMessage> getMessagesByRoomId(Long roomId);

    Optional<ChatMessage> getLastMessageByRoomId(Long roomId);
}
