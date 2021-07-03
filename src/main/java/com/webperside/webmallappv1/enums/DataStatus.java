package com.webperside.webmallappv1.enums;

public enum DataStatus {

    DEACTIVE(0),
    ACTIVE(1);

    private final byte value;

    DataStatus(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }
}
