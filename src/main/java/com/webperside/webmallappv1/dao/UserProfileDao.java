package com.webperside.webmallappv1.dao;

import com.webperside.webmallappv1.model.UserProfile;

public interface UserProfileDao {

    int save(UserProfile userProfile);

    UserProfile findByUserId(Integer userId);

    UserProfile findById(Integer id);

    int update(UserProfile userProfile);
}
