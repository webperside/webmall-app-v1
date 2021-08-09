package com.webperside.webmallappv1.dao;

import com.webperside.webmallappv1.model.UserContact;

import java.util.List;

public interface UserContactDao {

    List<UserContact> getUserContacts(Integer userId);

}
