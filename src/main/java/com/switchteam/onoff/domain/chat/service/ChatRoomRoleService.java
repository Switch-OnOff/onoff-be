package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.dto.ChatRoomResponseDto;

import java.util.List;

public interface ChatRoomRoleService {

    /**
     * 사용자 ID와 연관된 모든 채팅방 목록을 조회합니다.
     * 구매자 또는 판매자 역할에 관계없이 해당 사용자가 참여한 모든 채팅방을 반환합니다.
     *
     * @param userId 채팅방을 조회할 사용자의 ID
     * @return 해당 사용자와 연관된 ChatRoomResponseDto 객체 목록
     */
    List<ChatRoomResponseDto> getAllRoomByUserId(Long userId);
}
