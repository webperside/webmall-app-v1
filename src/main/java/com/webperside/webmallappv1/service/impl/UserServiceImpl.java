package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.dao.*;
import com.webperside.webmallappv1.dto.UserRegisterDto;
import com.webperside.webmallappv1.enums.*;
import com.webperside.webmallappv1.model.*;
import com.webperside.webmallappv1.service.UserService;
import com.webperside.webmallappv1.util.DigestUtil;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.webperside.webmallappv1.util.MailUtil.sendRegistrationEmail;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = ContextDao.userDaoInstance();
    private final UserProfileDao userProfileDao = ContextDao.userProfileDaoInstance();
    private final UserSecurityDao userSecurityDao = ContextDao.userSecurityDaoInstance();
    private final RoleDao roleDao = ContextDao.roleDaoInstance();
    private final UserRoleDao userRoleDao = ContextDao.userRoleDaoInstance();
    private final UserContactDao userContactDao = ContextDao.userContactDaoInstance();

    @Override
    public Integer register(UserRegisterDto userRegisterDto) {

        boolean result = userDao.checkUserExistsByUsername(userRegisterDto.getUsername());

        if(result){
            return -1;
        }

        User user = saveUser(userRegisterDto);

        saveUserProfile(user, userRegisterDto);

        saveUserRoles(user);

        saveDefaultUserContacts(user);

        String emailConfirmationCode = saveUserSecurity(user);

        sendRegistrationEmail(user.getUsername(), emailConfirmationCode);

        return user.getUserType().equals(UserType.USER) ? 0 : user.getUserId(); // if userType is Company
    }

    @Override
    public Integer confirmUser(String code) {
        UserSecurity us = userSecurityDao.findByEmailConfirmationCode(code);

        if(us == null) return -1;

        us.setEmailConfirmation(EmailConfirmation.CONFIRMED);
        us.setEmailConfirmationCode(null);
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

    private void saveDefaultUserContacts(User user) {
        List<UserContact> userContacts = prepareDefaultUserContactList(user);
        userContactDao.save(userContacts);
    }

    private List<UserContact> prepareDefaultUserContactList(User user){
        List<UserContact> userContacts = new ArrayList<>();

        for(ContactType type : ContactType.values()){
            UserContact userContact = new UserContact();
            userContact.setContact(null);
            userContact.setUser(user);
            userContact.setContactType(type);
            userContact.setCreatedAt(Instant.now());
            userContact.setDataStatus(DataStatus.ACTIVE);
            userContacts.add(userContact);
        }

        return userContacts;
    }
}
