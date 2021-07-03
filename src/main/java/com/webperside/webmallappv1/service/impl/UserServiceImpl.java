package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dto.MailDto;
import com.webperside.webmallappv1.dto.UserRegisterDto;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.enums.UserStatus;
import com.webperside.webmallappv1.enums.UserType;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.service.MailService;
import com.webperside.webmallappv1.service.UserService;
import com.webperside.webmallappv1.util.DigestUtil;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class UserServiceImpl implements UserService {


    @Override
    public Integer register(UserRegisterDto userRegisterDto) {

        UserDao userDao = ContextDao.userDaoInstance();

        boolean result = userDao.checkUserExistsByUsername(userRegisterDto.getUsername());

        System.out.println(result);

        if(result){
            return -1;
        }

        User user = prepareUser(userRegisterDto);

        int userId = userDao.save(user);

        sendRegistrationEmail(user.getUsername());

        return userId;
    }

    private User prepareUser(UserRegisterDto userRegister) {
        User user = new User();
        user.setUsername(userRegister.getUsername());
        try {
            user.setPassword(DigestUtil.md5(userRegister.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setUserType(UserType.getByValue(userRegister.getUserType()));
        user.setUserStatus(UserStatus.NEW);
        user.setCreatedAt(Instant.now());
        user.setDataStatus(DataStatus.ACTIVE);
        return user;
    }

    private void sendRegistrationEmail(String to){
        MailDto mail = new MailDto();
        mail.setTo(to);
        mail.setSubject("Confirm Registration");
        mail.setBody("Use this link to confirm your profile : [LINK]");

        MailService mailService = ContextLogic.mailServiceInstance();

        mailService.send(mail);
    }
}
