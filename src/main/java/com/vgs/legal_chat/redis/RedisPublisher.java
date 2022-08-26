package com.vgs.legal_chat.redis;

import com.vgs.legal_chat.domain.model.ChatMessage;
import com.vgs.legal_chat.domain.model.ChatMessagePayload;
import com.vgs.legal_chat.domain.model.ChatRoom;
import com.vgs.legal_chat.repository.ChatMessageRepository;
import com.vgs.legal_chat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedisPublisher {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public void publish(ChannelTopic topic, ChatMessagePayload message) {
        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId())
                .orElseGet(() -> ChatRoom.getBuilder()
                        .withName(UUID.randomUUID().toString())
                        .build());

        ChatMessage publishedMessage = ChatMessage.getBuilder()
                .withChatRoom(chatRoom)
                .withContent(message.getContent())
                .withSender(message.getSender())
                .withType(message.getMessageType())
                .build();

        chatRoom.addMessage(publishedMessage);
        chatMessageRepository.save(publishedMessage);
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
