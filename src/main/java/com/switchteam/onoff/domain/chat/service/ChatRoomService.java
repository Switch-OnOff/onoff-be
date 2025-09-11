package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * 채팅방 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * <p>
 * 채팅방 생성, 조회, 삭제 기능을 제공합니다.
 * </p>
 */
public interface ChatRoomService {

    /**
     * 구매자와 판매자 정보를 기반으로 새로운 채팅방을 생성합니다.
     *
     * @param buyer  채팅방을 생성할 구매자 정보
     * @param seller 채팅방을 생성할 판매자 정보
     * @return 생성된 ChatRoom 객체
     */
    ChatRoom createRoom(User buyer, User seller);

    /**
     * 채팅방 ID를 이용하여 채팅방 정보를 조회합니다.
     *
     * @param roomId 조회할 채팅방의 ID
     * @return 조회된 ChatRoom 객체(Optional)
     */
    Optional<ChatRoom> getRoom(Long roomId);

    /**
     * 구매자 ID와 연관된 모든 채팅방 목록을 조회합니다.
     *
     * @param buyerId 채팅방을 조회할 구매자의 ID
     * @return 해당 구매자와 연관된 ChatRoom 객체 목록
     */
    List<ChatRoom> getAllRoomByBuyerId(Long buyerId);

    /**
     * 판매자 ID와 연관된 모든 채팅방 목록을 조회합니다.
     *
     * @param sellerId 채팅방을 조회할 판매자의 ID
     * @return 해당 판매자와 연관된 ChatRoom 객체 목록
     */
    List<ChatRoom> getAllRoomBySellerId(Long sellerId);

    /**
     * 채팅방 ID를 이용하여 채팅방을 삭제합니다.
     *
     * @param roomId 삭제할 채팅방의 ID
     */
    void deleteRoom(Long roomId);
}