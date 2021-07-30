package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.dao.UserSecurityDao;
import com.webperside.webmallappv1.dto.SessionUserDetails;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.model.UserSecurity;
import com.webperside.webmallappv1.service.SecurityService;
import com.webperside.webmallappv1.util.DigestUtil;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

public class SecurityServiceImpl implements SecurityService {

    private final UserDao userDao = ContextDao.userDaoInstance();
    private final UserSecurityDao userSecurityDao = ContextDao.userSecurityDaoInstance();
    private final UserProfileDao userProfileDao = ContextDao.userProfileDaoInstance();

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
                    //TODO
                    // SessionUserDetails(id, fullName, username, list<String> roles)
                    // STEP 1.0 findUserProfileByUserId(user.getId());
                    // SessionUserDetails.setFullName(userProfile.getName() + " " + userProfile.getSurname())
                    // STEP 2.0
                    // SessionUserDetails.setUsername(user.getUsername());
                    // STEP 3.0
                    // STEP 4.0 ROLE
                    System.out.println("SESSION USER DETAILS " + " ");
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

    private SessionUserDetails prepareSessionUserDetails(User user) {
        UserProfile userProfile = userProfileDao.findByUserId(user.getUserId()); //buralari artiq ozun yaz push ele. bayaq sehv branchda ishlemisdin
        UserSecurity userSecurity = userSecurityDao.findByUserId(user.getUserId());
        SessionUserDetails sessionUserDetails = new SessionUserDetails();
        sessionUserDetails.setId(user.getUserId());
        sessionUserDetails.setUsername(user.getUsername());
        sessionUserDetails.setFullName(userProfile.getName() + " " + userProfile.getSurname());
        return sessionUserDetails;
    }
}
