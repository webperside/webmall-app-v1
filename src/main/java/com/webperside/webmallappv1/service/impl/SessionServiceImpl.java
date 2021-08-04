package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.dao.UserSecurityDao;
import com.webperside.webmallappv1.dto.SessionUserDetails;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.model.UserSecurity;
import com.webperside.webmallappv1.service.SessionService;

import javax.servlet.http.HttpServletRequest;

public class SessionServiceImpl implements SessionService {

    private final UserSecurityDao userSecurityDao = ContextDao.userSecurityDaoInstance();
    private final UserProfileDao userProfileDao = ContextDao.userProfileDaoInstance();

    @Override
    public void setSessionUserDetails(HttpServletRequest req, User user) {
        SessionUserDetails details = prepareSessionUserDetails(user);
        System.out.println(details);
        req.getSession().setAttribute("loggedUser", details);
    }

    private SessionUserDetails prepareSessionUserDetails(User user) {
        UserProfile userProfile = userProfileDao.findByUserId(user.getUserId()); //buralari artiq ozun yaz push ele. bayaq sehv branchda ishlemisdin
        UserSecurity userSecurity = userSecurityDao.findByUserId(user.getUserId());
        SessionUserDetails sessionUserDetails = new SessionUserDetails();
        sessionUserDetails.setId(user.getUserId());
        sessionUserDetails.setUsername(user.getUsername());
        sessionUserDetails.setFullName(userProfile.getName() + " " + userProfile.getSurname());
        sessionUserDetails.setGender(userProfile.getGender().getName());
        return sessionUserDetails;
    }


}
