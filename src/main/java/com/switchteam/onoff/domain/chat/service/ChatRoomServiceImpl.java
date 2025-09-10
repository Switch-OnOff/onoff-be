package com.switchteam.onoff.domain.chat.service;

import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import com.switchteam.onoff.domain.chat.repsoitory.ChatRoomRepository;
import com.switchteam.onoff.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoom createRoom(User buyer, User seller) {
        ChatRoom room = ChatRoom.builder()
                .buyer(buyer)
                .seller(seller)
                .build();

        return chatRoomRepository.save(room);
    }

    @Override
    public Optional<ChatRoom> getRoom(Long roomId) {
        return chatRoomRepository.findById(roomId);
    }

    @Override
    public void deleteRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }
}
