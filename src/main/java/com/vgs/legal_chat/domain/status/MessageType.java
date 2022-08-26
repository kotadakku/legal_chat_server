package com.vgs.legal_chat.domain.status;

public enum MessageType {
    ENTER(0, "chat room entry"),
    EXIT(1, "leave chat room"),
    TALK(2, "send message"),
    PROFILE_REQUEST(3, "Request a counterparty profile");

    private final int code;
    private final String name;

    MessageType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
