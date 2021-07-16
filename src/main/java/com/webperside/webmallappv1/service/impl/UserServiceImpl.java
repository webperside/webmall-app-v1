package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dao.*;
import com.webperside.webmallappv1.dto.MailDto;
import com.webperside.webmallappv1.dto.UserRegisterDto;
import com.webperside.webmallappv1.enums.*;
import com.webperside.webmallappv1.model.Role;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.model.UserSecurity;
import com.webperside.webmallappv1.service.MailService;
import com.webperside.webmallappv1.service.UserService;
import com.webperside.webmallappv1.util.DigestUtil;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = ContextDao.userDaoInstance();
    private final UserProfileDao userProfileDao = ContextDao.userProfileDaoInstance();
    private final UserSecurityDao userSecurityDao = ContextDao.userSecurityDaoInstance();
    private final RoleDao roleDao = ContextDao.roleDaoInstance();
    private final UserRoleDao userRoleDao = ContextDao.userRoleDaoInstance();
    private final MailService mailService = ContextLogic.mailServiceInstance();

    @Override
    public Integer register(UserRegisterDto userRegisterDto) {

        boolean result = userDao.checkUserExistsByUsername(userRegisterDto.getUsername());

        if(result){
            return -1;
        }

        User user = saveUser(userRegisterDto);

        saveUserProfile(user, userRegisterDto);

        String emailConfirmationCode = saveUserSecurity(user);

        saveUserRoles(user);

        if(user.getUserType().equals(UserType.USER)){
            sendRegistrationEmail(user.getUsername(), emailConfirmationCode);
            return 0;
        }

        return user.getUserId(); // if userType is Company
    }

    @Override
    public Integer confirmUser(String code) {
        UserSecurity us = userSecurityDao.findByEmailConfirmationCode(code);

        if(us == null) return -1;

        us.setEmailConfirmation(EmailConfirmation.CONFIRMED);
        us.setModifiedAt(Instant.now());
        userSecurityDao.update(us);

        User user = userDao.findById(us.getUser().getUserId());
        user.setUserStatus(UserStatus.APPROVED);
        user.setModifiedAt(Instant.now());

        userDao.update(user);

        return user.getUserId();
    }

    private String saveUserSecurity(User user) {

        UserSecurity us = prepareUserSecurity(user);

        userSecurityDao.save(us);

        return us.getEmailConfirmationCode();
    }

    private User saveUser(UserRegisterDto userRegisterDto){
        User user = prepareUser(userRegisterDto);

        int userId = userDao.save(user);
        user.setUserId(userId);

        return user;
    }

    private void saveUserProfile(User user, UserRegisterDto userRegister){
        UserProfile userProfile = prepareUserProfile(user, userRegister);

        userProfileDao.save(userProfile);
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

    private UserProfile prepareUserProfile(User user, UserRegisterDto userRegister){
        UserProfile up = new UserProfile();
        up.setUser(user);
        up.setName(userRegister.getName());
        up.setSurname(userRegister.getSurname());
        up.setGender(Gender.getByValue(userRegister.getGender()));
        up.setCreatedAt(Instant.now());
        up.setDataStatus(DataStatus.ACTIVE);

        return up;
    }

    private UserSecurity prepareUserSecurity(User user){
        UserSecurity us = new UserSecurity();

        us.setUser(user);
        us.setEmailConfirmation(EmailConfirmation.NOT_CONFIRMED);
        us.setEmailConfirmationCode(UUID.randomUUID().toString());
        us.setCreatedAt(Instant.now());
        us.setDataStatus(DataStatus.ACTIVE);

        return us;
    }

    private void saveUserRoles(User user){
        Role role = roleDao.findByName("CUSTOMER");

        List<Integer> rolesIds = new ArrayList<>(Collections.singletonList(role.getRoleId()));

        if(user.getUserType().equals(UserType.COMPANY)){
            role = roleDao.findByName("COMPANY_ADMIN");

            rolesIds.add(role.getRoleId());
        }

        userRoleDao.save(user.getUserId(), rolesIds);
    }

    private void sendRegistrationEmail(String to, String code){
        String url = "http://localhost:8080/confirm-registration?code="+code;

        MailDto mail = new MailDto();
        mail.setTo(to);
        mail.setSubject("Confirm Registration");
//        mail.setBody("Use this link to confirm your profile : [LINK]".replaceAll("[LINK]",url));
        mail.setBody("Use this link to confirm your profile : " + url);

        mailService.send(mail);
    }
}
