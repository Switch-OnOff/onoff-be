package com.switchteam.onoff.domain.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDto {
    private Long roomId;
    private Long senderId;
    private String content;
}
