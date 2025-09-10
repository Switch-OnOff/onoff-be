package com.switchteam.onoff.domain.chat.repsoitory;

import com.switchteam.onoff.domain.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByBuyerIdOrderByCreatedAtDesc(Long buyerId);
    List<ChatRoom> findAllBySellerIdOrderByCreatedAtDesc(Long sellerId);
}
