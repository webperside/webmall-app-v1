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
            else {
                if (user.getUserStatus().getValue() == 0) return 0;
                else if (user.getUserStatus().getValue() == 2) return 2;
                else if (user.getUserStatus().getValue() == 3) return 3;
                else {
                    req.getSession().setAttribute("loggedUser", user);
                    return 1;
                }
            }
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
