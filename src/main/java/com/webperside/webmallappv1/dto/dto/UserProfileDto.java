package com.webperside.webmallappv1.dto.dto;

import com.webperside.webmallappv1.model.UserProfile;

import java.time.LocalDate;

public class UserProfileDto {

    private UserProfile userProfileId;
    private String fullName;
    private String avatar;
    private LocalDate birthDate;
    private String gender;
    private boolean isMe;

    public UserProfileDto() {
    }

    public UserProfileDto(UserProfile userProfileId, String fullName, String avatar, LocalDate birthDate, String gender, boolean isMe) {
        this.userProfileId = userProfileId;
        this.fullName = fullName;
        this.avatar = avatar;
        this.birthDate = birthDate;
        this.gender = gender;
        this.isMe = isMe;
    }

    public UserProfile getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        this.userProfileId = userProfileId;
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
}
