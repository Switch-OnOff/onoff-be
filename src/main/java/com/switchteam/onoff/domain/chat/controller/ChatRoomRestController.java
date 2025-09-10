package com.switchteam.onoff.domain.chat.controller;

import com.switchteam.onoff.domain.chat.dto.ChatRoomResponseDto;
import com.switchteam.onoff.domain.chat.service.ChatRoomRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "채팅 관련 API", description = "채팅 관련 API입니다")
@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomRestController {

    private final ChatRoomRoleService chatRoomRoleService;

    /**
     * 주어진 사용자 ID에 해당하는 모든 채팅방을 조회합니다.
     *
     * @param userId 사용자의 고유 식별자
     * @return 해당 사용자가 참여한 모든 채팅방의 목록
     */
    @GetMapping("/{userId}")
    public List<ChatRoomResponseDto> getAllRooms(@PathVariable Long userId) {
        return chatRoomRoleService.getAllRoomByUserId(userId);
    }
}
