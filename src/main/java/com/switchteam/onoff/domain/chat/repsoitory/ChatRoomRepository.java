package com.switchteam.onoff.domain.chat.repsoitory;

import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
