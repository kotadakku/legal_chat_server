package com.vgs.legal_chat.repository;

import com.vgs.legal_chat.domain.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT c FROM ChatMessage AS c " +
            "WHERE c.chatRoom.id = :id")
    List<ChatMessage> findAllByChatRoomId(
            @Param("id") Long id);
}
