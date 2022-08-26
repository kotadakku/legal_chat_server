package com.vgs.legal_chat.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vgs.legal_chat.domain.status.MessageType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessagePayload {
    @NotBlank
    private String content;

    @NotBlank
    private String sender;

    @NotBlank
    @JsonProperty(value = "message_type")
    private MessageType messageType;

    @NotNull
    @JsonProperty(value = "chat_room_id")
    private Long chatRoomId;
}
