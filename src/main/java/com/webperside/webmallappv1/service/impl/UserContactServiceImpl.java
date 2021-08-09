package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dao.UserContactDao;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dto.SessionUserDetails;
import com.webperside.webmallappv1.dto.user.UserContactDto;
import com.webperside.webmallappv1.model.UserContact;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.service.SessionService;
import com.webperside.webmallappv1.service.UserContactService;
import com.webperside.webmallappv1.util.AuthenticationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserContactServiceImpl implements UserContactService {

    private final UserContactDao userContactDao = ContextDao.userContactDaoInstance();

    @Override
    public List<UserContactDto> getUserContact(HttpServletRequest req) {
        SessionUserDetails details = AuthenticationUtil.getAuthentication(req);
        List<UserContact> userContacts = userContactDao.getUserContacts(details.getId());
    }
}
