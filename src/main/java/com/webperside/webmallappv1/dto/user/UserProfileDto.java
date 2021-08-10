package com.webperside.webmallappv1.dto.user;


import java.time.LocalDate;
import java.util.List;

public class UserProfileDto {

    private String fullName;
    private String avatar;
    private LocalDate birthDate;
    private String gender;
    private Boolean isMe;
    private List<UserContactDto> contacts;

    public UserProfileDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public List<UserContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<UserContactDto> contacts) {
        this.contacts = contacts;
    }
}
