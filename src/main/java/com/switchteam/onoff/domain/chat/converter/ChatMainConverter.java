package com.switchteam.onoff.domain.chat.converter;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;
import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.dto.ChatMainResponseDto;
import com.switchteam.onoff.domain.chat.enums.ChatRole;
import com.switchteam.onoff.domain.user.domain.User;

public class ChatMainConverter {

    /**
     * 주어진 ChatRoom과 ChatMessage 엔티티를 현재 사용자의 채팅방 요약 정보를 담은
     * ChatMainUnitResponseDto로 변환합니다.
     *
     * @param room        변환할 채팅방 엔티티
     * @param lastMessage 채팅방의 마지막 메시지 (메시지가 없는 경우 null)
     * @param currentUser 채팅방을 조회하는 현재 사용자 (역할 판단에 사용)
     * @return 사용자의 역할, 상대방 정보, 마지막 메시지 정보 등을 포함한
     * ChatMainResponseDto.ChatMainUnitResponseDto 객체
     */
    public static ChatMainResponseDto.ChatMainUnitResponseDto toUnitDto(ChatRoom room, ChatMessage lastMessage, User currentUser) {
        User otherUser;
        ChatRole myRole;

        if (room.getBuyer().equals(currentUser)) {
            otherUser = room.getSeller();
            myRole = ChatRole.BUYER;
        } else {
            otherUser = room.getBuyer();
            myRole = ChatRole.SELLER;
        }

        return ChatMainResponseDto.ChatMainUnitResponseDto.builder()
                .roomId(room.getRoomId())
                .myRole(myRole)
                .otherUserId(otherUser.getUserId())
                .otherUserName(otherUser.getName())
                .lastMessage(lastMessage != null ? lastMessage.getContent() : "")
                .lastMessageTime(lastMessage != null ? lastMessage.getSentAt() : null)
                .build();
    }
}