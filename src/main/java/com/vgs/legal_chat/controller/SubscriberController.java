package com.vgs.legal_chat.controller;

import com.vgs.legal_chat.domain.model.ChatRoom;
import com.vgs.legal_chat.redis.RedisSubscriber;
import com.vgs.legal_chat.repository.ChatMessageRepository;
import com.vgs.legal_chat.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/chat")
public class SubscriberController {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private RedisSubscriber redisSubscriber;

    @Autowired
    private RedisMessageListenerContainer redisMessageListener;  // 채팅방(Topic)에 발행되는 메시지를 처리할 Listener(Subscriber)

    @PostMapping
    public ChatRoom createChatRoom(@RequestParam String name) {
        ChatRoom newChatRoom = ChatRoom.getBuilder()
                .withName(name)
                .build();
        newChatRoom = chatRoomRepository.save(newChatRoom);
        ChannelTopic topic = ChannelTopic.of(newChatRoom.getId().toString());
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        return newChatRoom;
    }

    @GetMapping
    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    @GetMapping(path = "/rooms/{chatRoomId}/messages")
    public ResponseEntity<?> getChatMessages(@PathVariable("chatRoomId") Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);

        if (chatRoom != null) {
            return new ResponseEntity<>(chatRoom.getMessages(), HttpStatus.OK);
        }
        log.error("Error message - chat room not found with id: {}", chatRoomId);
        return new ResponseEntity<>("Error message - chat room not found", HttpStatus.NOT_FOUND);
    }
}
