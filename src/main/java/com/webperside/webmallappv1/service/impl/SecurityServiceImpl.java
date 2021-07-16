package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.service.SecurityService;
import com.webperside.webmallappv1.util.DigestUtil;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

public class SecurityServiceImpl implements SecurityService {

    private final UserDao userDao = ContextDao.userDaoInstance();

    @Override
    public int login(HttpServletRequest req, String username, String password) {
        try {

            password = DigestUtil.md5(password);

            User user = userDao.findByUsernameAndPassword(username, password);

            if(user == null) return -1;

            req.getSession().setAttribute("loggedUser",user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public void logout(HttpServletRequest req) {
        req.getSession().setAttribute("loggedUser", null);
    }
}
