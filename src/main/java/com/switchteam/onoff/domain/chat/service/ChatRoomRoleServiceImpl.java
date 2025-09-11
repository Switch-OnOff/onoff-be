package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.converter.ChatRoomConverter;
import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.dto.ChatRoomResponseDto;
import com.switchteam.onoff.domain.chat.enums.ChatRole;
import com.switchteam.onoff.domain.user.service.UserService;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomRoleServiceImpl implements ChatRoomRoleService {

    private final ChatRoomService chatRoomService;
    private final UserService userService;

    public List<ChatRoomResponseDto> getAllRoomByUserId(Long userId) {
        // 사용자 존재 확인
        userService.getUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<ChatRoom> buyerRooms = chatRoomService.getAllRoomByBuyerId(userId);
        List<ChatRoom> sellerRooms = chatRoomService.getAllRoomBySellerId(userId);

        List<ChatRoomResponseDto> chatRoomResponseDtos = new ArrayList<>();

        buyerRooms.forEach(room -> chatRoomResponseDtos.add(ChatRoomConverter.toDto(room, ChatRole.BUYER)));
        sellerRooms.forEach(room -> chatRoomResponseDtos.add(ChatRoomConverter.toDto(room, ChatRole.SELLER)));

        // 생성일 기준 내림차순 정렬
        chatRoomResponseDtos.sort(Comparator.comparing(ChatRoomResponseDto::getRoomId).reversed());

        return chatRoomResponseDtos;
    }
}
