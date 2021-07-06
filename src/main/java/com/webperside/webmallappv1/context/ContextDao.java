package com.webperside.webmallappv1.context;

import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.dao.UserSecurityDao;
import com.webperside.webmallappv1.dao.impl.UserDaoImpl;
import com.webperside.webmallappv1.dao.impl.UserProfileDaoImpl;
import com.webperside.webmallappv1.dao.impl.UserSecurityDaoImpl;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.model.UserSecurity;

public class ContextDao {

    public static UserDao userDaoInstance(){
        return new UserDaoImpl();
    }

    public static UserProfileDao userProfileDaoInstance(){
        return new UserProfileDaoImpl();
    }

    public static UserSecurityDao userSecurityDaoInstance(){
        return new UserSecurityDaoImpl();
    }
}
