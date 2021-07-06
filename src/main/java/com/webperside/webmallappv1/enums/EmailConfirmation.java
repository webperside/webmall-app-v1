package com.webperside.webmallappv1.enums;

public enum EmailConfirmation {

    NOT_CONFIRMED(0),
    CONFIRMED(1);

    private final byte value;

    EmailConfirmation(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public static EmailConfirmation getByValue(int value){
        for(EmailConfirmation emailConfirmation : values()){
            if(value == emailConfirmation.getValue()){
                return emailConfirmation;
            }
        }
        return null;
    }
}
