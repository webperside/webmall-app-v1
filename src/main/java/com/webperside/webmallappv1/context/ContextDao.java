package com.webperside.webmallappv1.context;

import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dao.impl.UserDaoImpl;

public class ContextDao {

    public static UserDao userDaoInstance(){
        return new UserDaoImpl();
    }
}
