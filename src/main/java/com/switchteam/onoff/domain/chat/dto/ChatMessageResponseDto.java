package com.switchteam.onoff.domain.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponseDto {
    private Long chatMessageId;
    private Long roomId;
    private Long senderId;
    private String content;
    private LocalDateTime sentAt;
}

