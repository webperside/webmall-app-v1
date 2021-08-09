package com.webperside.webmallappv1.dto.user;

import com.webperside.webmallappv1.enums.ContactType;

public class UserContactDto {

    private Integer id;
    private String contact;
    private ContactType contactType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
