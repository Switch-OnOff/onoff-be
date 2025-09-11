package com.switchteam.onoff.domain.chat.controller;

import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.service.ChatRoomService;
import com.switchteam.onoff.domain.user.domain.User;
import com.switchteam.onoff.domain.user.service.UserService;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final SimpMessagingTemplate template;

    /**
     * 구매자와 판매자 간의 새로운 채팅방을 생성합니다. 제공된 ID를 사용하여 사용자 세부 정보를 조회하고
     * 사용자를 찾을 수 없는 경우 예외를 처리합니다. 생성된 채팅방은 구독자에게 브로드캐스트됩니다.
     *
     * @param buyerId  구매자의 고유 식별자
     * @param sellerId 판매자의 고유 식별자
     */
    @MessageMapping("/chat/createRoom")
    public void createRoom(Long buyerId, Long sellerId) {
        User buyer = userService.getUserById(buyerId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User seller = userService.getUserById(sellerId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom room = chatRoomService.createRoom(buyer, seller);

        // 생성된 채팅방 정보 브로드캐스트
        template.convertAndSend("/topic/chat/roomCreated", room);
    }
}
