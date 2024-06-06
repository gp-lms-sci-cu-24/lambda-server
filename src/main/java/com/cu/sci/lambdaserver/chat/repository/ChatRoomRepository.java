package com.cu.sci.lambdaserver.chat.repository;

import com.cu.sci.lambdaserver.chat.entites.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
