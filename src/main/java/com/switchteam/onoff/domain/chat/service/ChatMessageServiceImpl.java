package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;
import com.switchteam.onoff.domain.chat.repsoitory.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessagesByRoomId(Long roomId) {
        return chatMessageRepository.findAllByRoom_RoomIdOrderBySentAtAsc(roomId);
    }

    @Override
    public Optional<ChatMessage> getLastMessageByRoomId(Long roomId) {
        return chatMessageRepository.findTopByRoom_RoomIdOrderBySentAtDesc(roomId);
    }
}
