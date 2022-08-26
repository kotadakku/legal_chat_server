package com.vgs.legal_chat.controller;

import com.vgs.legal_chat.domain.model.ChatMessagePayload;
import com.vgs.legal_chat.domain.status.MessageType;
import com.vgs.legal_chat.redis.RedisPublisher;
import com.vgs.legal_chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {
    @Autowired
    private RedisPublisher redisPublisher;

    @Autowired
    private ChatService chatService;

    @MessageMapping(value = "/chat/message")
    public void sendMessage(ChatMessagePayload message) {
        if (MessageType.ENTER.equals(message.getMessageType())) {
            chatService.enterChatRoom(message.getChatRoomId());
//            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(ChannelTopic.of(message.getChatRoomId().toString()), message);
    }
}
