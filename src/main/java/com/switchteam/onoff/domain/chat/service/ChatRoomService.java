package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.user.domain.User;

import java.util.Optional;

public interface ChatRoomService {

    ChatRoom createRoom(User buyer, User seller);

    Optional<ChatRoom> getRoom(Long roomId);

    void deleteRoom(Long roomId);}
