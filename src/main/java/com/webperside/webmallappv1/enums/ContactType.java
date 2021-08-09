package com.webperside.webmallappv1.enums;

public enum ContactType {

    PHONE(0, "Phone"),
    EMAIL(1, "Email"),
    ADDRESS(2, "Address");

    private final byte value;
    private final String name;

    ContactType(int value, String name) {
        this.value = (byte) value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ContactType getByValue(int value) {
        for (ContactType contactType : ContactType.values()) {
            if (contactType.getValue() == value) {
                return contactType;
            }
        }
        return null;
    }
}
