package com.webperside.webmallappv1.dao;

import com.webperside.webmallappv1.model.UserSecurity;

public interface UserSecurityDao {

    int save(UserSecurity userSecurity);

    UserSecurity findByEmailConfirmationCode(String code);

    UserSecurity findByUserId(Integer userId);

    int update(UserSecurity userSecurity);
}
