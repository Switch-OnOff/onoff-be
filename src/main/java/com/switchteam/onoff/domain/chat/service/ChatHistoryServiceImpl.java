package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;
import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.dto.ChatRoomMessageResponseDto;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatHistoryServiceImpl implements ChatHistoryService {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @Override
    public ChatRoomMessageResponseDto getChatHistory(Long roomId) {
        ChatRoom room = chatRoomService.getRoom(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHATROOM_NOT_FOUND));

        List<ChatMessage> messages = chatMessageService.getMessagesByRoomId(roomId);

        List<ChatRoomMessageResponseDto.ChatMessageUnitDto> dtos = messages.stream()
                .map(m -> ChatRoomMessageResponseDto.ChatMessageUnitDto.builder()
                        .chatMessageId(m.getChatMessageId())
                        .senderId(m.getSenderId().getUserId())
                        .content(m.getContent())
                        .sentAt(m.getSentAt())
                        .build())
                .toList();

        return ChatRoomMessageResponseDto.builder()
                .roomId(room.getRoomId())
                .chatMessages(dtos)
                .build();
    }
}
