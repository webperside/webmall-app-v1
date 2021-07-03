package com.webperside.webmallappv1.enums;

public enum UserType {

    USER(0,"User"),
    COMPANY(1,"Company");

    private final byte value;
    private final String name;

    UserType(int value, String name) {
        this.value = (byte) value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static UserType getByValue(int value){
        for(UserType type: values()){
            if(type.getValue() == value){
                return type;
            }
        }
        return null;
    }
}
