package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.dao.CompanyDao;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.dao.UserSecurityDao;
import com.webperside.webmallappv1.dto.SessionUserDetailsDto;
import com.webperside.webmallappv1.model.Company;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.model.UserSecurity;
import com.webperside.webmallappv1.service.SessionService;

import javax.servlet.http.HttpServletRequest;

public class SessionServiceImpl implements SessionService {

    private final UserSecurityDao userSecurityDao = ContextDao.userSecurityDaoInstance();
    private final UserProfileDao userProfileDao = ContextDao.userProfileDaoInstance();
    private final CompanyDao companyDao = ContextDao.companyDaoInstance();

    @Override
    public void setSessionUserDetails(HttpServletRequest req, User user) {
        SessionUserDetailsDto details = prepareSessionUserDetails(user);
        System.out.println(details);
        req.getSession().setAttribute("loggedUser", details);
    }

    private SessionUserDetailsDto prepareSessionUserDetails(User user) {
        UserProfile userProfile = userProfileDao.findByUserId(user.getUserId()); //buralari artiq ozun yaz push ele. bayaq sehv branchda ishlemisdin
        UserSecurity userSecurity = userSecurityDao.findByUserId(user.getUserId());
        Company company = companyDao.findByUserId(user.getUserId());
        SessionUserDetailsDto sessionUserDetailsDto = new SessionUserDetailsDto();
        sessionUserDetailsDto.setId(user.getUserId());
        sessionUserDetailsDto.setUsername(user.getUsername());
        sessionUserDetailsDto.setFullName(userProfile.getName() + " " + userProfile.getSurname());
        sessionUserDetailsDto.setGender(userProfile.getGender().getName());

        if(company != null){
            sessionUserDetailsDto.setCompanyId(company.getCompanyId());
            sessionUserDetailsDto.setCompanyName(company.getName());
        }

        return sessionUserDetailsDto;
    }


}
