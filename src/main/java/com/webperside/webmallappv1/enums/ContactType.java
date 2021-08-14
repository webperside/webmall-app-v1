package com.webperside.webmallappv1.enums;

public enum ContactType {

    PHONE(0, "Phone","fas fa-phone"),
    EMAIL(1, "Email","fas fa-envelope"),
    ADDRESS(2, "Address","fas fa-map-marker-alt");

    private final byte value;
    private final String name;
    private final String icon;

    ContactType(int value, String name, String icon) {
        this.value =(byte) value;
        this.name = name;
        this.icon = icon;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
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
