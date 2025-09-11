package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;
import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.dto.ChatMessageRequestDto;
import com.switchteam.onoff.domain.chat.repsoitory.ChatMessageRepository;
import com.switchteam.onoff.domain.chat.repsoitory.ChatRoomRepository;
import com.switchteam.onoff.domain.user.domain.User;
import com.switchteam.onoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    @Override
    public ChatMessage sendMessage(ChatMessageRequestDto dto) {
        // DTO → 엔티티 변환
        ChatRoom room = chatRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatMessage message = ChatMessage.builder()
                .roomId(room)
                .senderId(sender)
                .content(dto.getContent())
                .sentAt(LocalDateTime.now())
                .build();

        // DB 저장
        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessagesByRoomId(Long roomId) {
        return chatMessageRepository.findAllByRoomId_RoomIdOrderBySentAtAsc(roomId);
    }

    @Override
    public Optional<ChatMessage> getLastMessageByRoomId(Long roomId) {
        return chatMessageRepository.findTopByRoomId_RoomIdOrderBySentAtDesc(roomId);
    }
}
