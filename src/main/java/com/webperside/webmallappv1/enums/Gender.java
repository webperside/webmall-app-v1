package com.webperside.webmallappv1.enums;

public enum Gender {
    MALE(0,"Male"),
    FEMALE(1,"Female"),
    UNKNOWN(2,"Unknown");

    private final byte value;
    private final String name;

    Gender(int value, String name) {
        this.value = (byte) value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Gender getByValue(int value){
        for(Gender gender : values()){
            if(value == gender.getValue()){
                return gender;
            }
        }
        return null;
    }
}
