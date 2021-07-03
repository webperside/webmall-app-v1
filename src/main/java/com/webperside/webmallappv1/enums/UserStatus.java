package com.webperside.webmallappv1.enums;

public enum UserStatus {
    DEACTIVE(0),
    APPROVED(1),
    NEW(2),
    BLOCKED(3);

    private final byte value;

    UserStatus(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }
}
