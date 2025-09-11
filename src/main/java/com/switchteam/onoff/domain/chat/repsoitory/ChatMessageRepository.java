package com.switchteam.onoff.domain.chat.repsoitory;

import com.switchteam.onoff.domain.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<ChatMessage> findTopByRoomId_RoomIdOrderBySentAtDesc(Long roomId);

    List<ChatMessage> findAllByRoomId_RoomIdOrderBySentAtAsc(Long roomId);
}
