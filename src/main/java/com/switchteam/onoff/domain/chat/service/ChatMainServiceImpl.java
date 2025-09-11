package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.converter.ChatMainConverter;
import com.switchteam.onoff.domain.chat.domain.ChatMessage;
import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.dto.ChatMainResponseDto;
import com.switchteam.onoff.domain.user.domain.User;
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
public class ChatMainServiceImpl implements ChatMainService {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    @Override
    public ChatMainResponseDto getMainChats(Long userId) {
        User currentUser = userService.getUserById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 내가 속한 채팅방 모두 조회
        List<ChatRoom> rooms = new ArrayList<>();
        rooms.addAll(chatRoomService.getAllRoomByBuyerId(userId));
        rooms.addAll(chatRoomService.getAllRoomBySellerId(userId));

        // DTO 변환
        List<ChatMainResponseDto.ChatMainUnitResponseDto> units = rooms.stream()
                .map(room -> {
                    ChatMessage lastMessage = chatMessageService
                            .getLastMessageByRoomId(room.getRoomId())
                            .orElse(null);
                    return ChatMainConverter.toUnitDto(room, lastMessage, currentUser);
                })
                .sorted(Comparator.comparing(ChatMainResponseDto.ChatMainUnitResponseDto::getLastMessageTime,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();

        return ChatMainResponseDto.builder()
                .chatUnits(units)
                .build();
    }
}
