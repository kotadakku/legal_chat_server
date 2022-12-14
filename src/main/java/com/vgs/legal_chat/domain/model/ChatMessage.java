package com.vgs.legal_chat.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vgs.legal_chat.domain.status.MessageType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(nullable = false)
    private String content;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private MessageType type;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(nullable = false)
    private String sender;

    @Setter(value = AccessLevel.PRIVATE)
    @JsonBackReference
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    public static ChatMessageBuilder getBuilder() {
        return new ChatMessageBuilder();
    }

    public static class ChatMessageBuilder {
        private String content;
        private MessageType type;
        private String sender;
        private ChatRoom chatRoom;

        public ChatMessageBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        public ChatMessageBuilder withType(MessageType type) {
            this.type = type;
            return this;
        }

        public ChatMessageBuilder withSender(String sender) {
            this.sender = sender;
            return this;
        }

        public ChatMessageBuilder withChatRoom(ChatRoom chatRoom) {
            this.chatRoom = chatRoom;
            return this;
        }

        public ChatMessage build() {
            ChatMessage message = new ChatMessage();
            message.setContent(content);
            message.setType(type);
            message.setSender(sender);
            message.setChatRoom(chatRoom);
            return message;
        }
    }
}
