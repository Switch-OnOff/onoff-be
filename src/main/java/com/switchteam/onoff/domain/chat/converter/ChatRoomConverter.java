package com.switchteam.onoff.domain.chat.converter;

import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.dto.ChatRoomResponseDto;
import com.switchteam.onoff.domain.chat.enums.ChatRole;

public class ChatRoomConverter {

    /**
     * ChatRoom 엔티티를 DTO로 변환
     *
     * @param room 변환할 ChatRoom 엔티티
     * @param role 사용자 관점 역할 ("BUYER" 또는 "SELLER")
     * @return ChatRoomResponseDto
     */
    public static ChatRoomResponseDto toDto(ChatRoom room, ChatRole role) {
        return ChatRoomResponseDto.builder()
                .roomId(room.getRoomId())
                .buyerId(room.getBuyer().getUserId())
                .sellerId(room.getSeller().getUserId())
                .role(role)
                .build();
    }
}
