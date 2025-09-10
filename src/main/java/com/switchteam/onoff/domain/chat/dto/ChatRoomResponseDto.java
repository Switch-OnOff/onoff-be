package com.switchteam.onoff.domain.chat.dto;

import com.switchteam.onoff.domain.chat.enums.ChatRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponseDto {
    private Long roomId;
    private Long buyerId;
    private Long sellerId;
    private ChatRole role; // "BUYER" 또는 "SELLER"
}
