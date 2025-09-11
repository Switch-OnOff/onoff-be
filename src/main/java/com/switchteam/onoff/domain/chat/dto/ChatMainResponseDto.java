package com.switchteam.onoff.domain.chat.dto;

import com.switchteam.onoff.domain.chat.enums.ChatRole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ChatMainResponseDto {
    private List<ChatMainUnitResponseDto> chatUnits;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ChatMainUnitResponseDto {
        private Long roomId;
        private ChatRole myRole;
        private Long otherUserId;
        private String otherUserName;
        private String lastMessage;
        private LocalDateTime lastMessageTime;
    }
}
