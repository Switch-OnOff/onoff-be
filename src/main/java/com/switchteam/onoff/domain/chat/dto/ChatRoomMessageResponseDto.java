package com.switchteam.onoff.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ChatRoomMessageResponseDto {

    private Long roomId;
    private List<ChatMessageUnitDto> chatMessages;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ChatMessageUnitDto {
        private Long chatMessageId;
        private Long senderId;
        private String senderName;
        private String content;
        private LocalDateTime sentAt;
    }
}
