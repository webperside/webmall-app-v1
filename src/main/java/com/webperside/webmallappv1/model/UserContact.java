package com.webperside.webmallappv1.model;

import com.webperside.webmallappv1.enums.ContactType;
import com.webperside.webmallappv1.enums.DataStatus;

import java.sql.Timestamp;
import java.time.Instant;

public class UserContact {

    private Integer userContactId;
    private User user;
    private String contact;
    private ContactType contactType;
    private Instant createdAt;
    private Instant modifiedAt;
    private DataStatus dataStatus;

    public UserContact() {
    }

    public Integer getUserContactId() {
        return userContactId;
    }

    public void setUserContactId(Integer userContactId) {
        this.userContactId = userContactId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public DataStatus getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(DataStatus dataStatus) {
        this.dataStatus = dataStatus;
    }
}
