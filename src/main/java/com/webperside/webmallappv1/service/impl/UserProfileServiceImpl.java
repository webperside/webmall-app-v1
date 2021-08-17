package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dao.UserContactDao;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.dto.SessionUserDetailsDto;
import com.webperside.webmallappv1.dto.user.UserContactDto;
import com.webperside.webmallappv1.dto.user.UserProfileDto;
import com.webperside.webmallappv1.dto.user.UserProfileEditDto;
import com.webperside.webmallappv1.enums.ContactType;
import com.webperside.webmallappv1.enums.Gender;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserContact;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.service.SessionService;
import com.webperside.webmallappv1.service.UserProfileService;
import com.webperside.webmallappv1.util.AuthenticationUtil;
import com.webperside.webmallappv1.util.HttpUtil;
import com.webperside.webmallappv1.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileDao userProfileDao = ContextDao.userProfileDaoInstance();
    private final UserDao userDao = ContextDao.userDaoInstance();
    private final SessionService sessionService = ContextLogic.sessionServiceInstance();
    private final UserContactDao userContactDao = ContextDao.userContactDaoInstance();

    @Override
    public UserProfileDto getUserProfile(HttpServletRequest req) {
        String[] params = req.getRequestURI().split("/"); // /user-profile

        UserProfile userProfile = null;
        Boolean isMe = null;

        if (params.length == 2) {
            userProfile = retrieveUserProfileOfAuthenticatedUser(req);
            isMe = true;
        } else if (params.length == 3 && StringUtils.isNumeric(params[2])) {
            Integer id = Integer.parseInt(params[2]);
            userProfile = userProfileDao.findByUserId(id);
            isMe = false;
        }

        return prepareUserProfileDto(userProfile, isMe);
    }

    @Override
    public UserProfileEditDto getUserProfileForEdit(HttpServletRequest req) {
        UserProfile userProfile = retrieveUserProfileOfAuthenticatedUser(req);
        List<UserContactDto> userContactDtos = getUserContacts(userProfile.getUser().getUserId());

        UserProfileEditDto edit = new UserProfileEditDto();
        edit.setId(userProfile.getUserProfileId());
        edit.setGender(userProfile.getGender().getValue());
        edit.setName(userProfile.getName());
        edit.setSurname(userProfile.getSurname());

        LocalDate birthdate = userProfile.getBirthdate();

        edit.setBirthdate(birthdate != null ? birthdate.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        ) : null);

        for(UserContactDto userContact : userContactDtos){
            if(userContact.getContactType().equals(ContactType.PHONE)) {
                edit.setPhone(userContact.getContact());
            }
            else if(userContact.getContactType().equals(ContactType.ADDRESS)) {
                edit.setAddress(userContact.getContact());
            }
            else {
                edit.setEmail(userContact.getContact());
            }
        }

        return edit;
    }

    @Override
    public int update(HttpServletRequest req, UserProfileEditDto editDto) {
        UserProfile userProfile = userProfileDao.findById(editDto.getId());
        List<UserContact> userContacts = userContactDao.getUserContacts(userProfile.getUser().getUserId());

        userProfile.setName(editDto.getName());
        userProfile.setSurname(editDto.getSurname());
        userProfile.setGender(Gender.getByValue(editDto.getGender()));

        String birthdate = editDto.getBirthdate();
        if(birthdate != null && !birthdate.isEmpty()) {
            userProfile.setBirthdate(LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } else {
            userProfile.setBirthdate(null);
        }

        userProfile.setModifiedAt(Instant.now());

        int code = userProfileDao.update(userProfile);

        if (code > 0) {
            User user = userDao.findById(userProfile.getUser().getUserId());
            updateUserContacts(userContacts, editDto);
            sessionService.setSessionUserDetails(user);
        }

        return code;
    }

    @Override
    public void setAvatar(Part part) {
        try {
            String uniqueName = UUID.randomUUID().toString() + "." +part.getContentType().split("/")[1];
            part.write(System.getenv("FILE_STORE_URL")+uniqueName);

            UserProfile userProfile = retrieveUserProfileOfAuthenticatedUser(HttpUtil.REQUEST);
            userProfile.setAvatar(uniqueName);
            userProfile.setModifiedAt(Instant.now());
            userProfileDao.update(userProfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateUserContacts(List<UserContact> userContacts, UserProfileEditDto editDto){
        for(UserContact userContact : userContacts) {
            if(userContact.getContactType().equals(ContactType.PHONE)) {
                userContact.setContact(editDto.getPhone().isEmpty() ? null : editDto.getPhone());
            }
            else if(userContact.getContactType().equals(ContactType.ADDRESS)) {
                userContact.setContact(editDto.getAddress().isEmpty() ? null : editDto.getAddress());
            }
            else {
                userContact.setContact(editDto.getEmail().isEmpty() ? null : editDto.getEmail());
            }
            userContact.setModifiedAt(Instant.now());
        }

        userContactDao.update(userContacts);
    }

    private UserProfileDto prepareUserProfileDto(UserProfile userProfile, Boolean isMe) {

        if (userProfile == null) return null;

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setAvatar("avatar"); // todo
        userProfileDto.setGender(userProfile.getGender().getName());
        userProfileDto.setFullName(userProfile.getName() + " " + userProfile.getSurname());
        userProfileDto.setBirthDate(userProfile.getBirthdate());
        userProfileDto.setMe(isMe);
        userProfileDto.setContacts(getUserContacts(userProfile.getUser().getUserId()));
        return userProfileDto;
    }

    private List<UserContactDto> getUserContacts(Integer userId) {
        List<UserContact> userContacts = userContactDao.getUserContacts(userId);
        List<UserContactDto> userContactDtos = new ArrayList<>();
        for (UserContact userContact : userContacts) {
            UserContactDto userContactDto = new UserContactDto();
            userContactDto.setId(userContact.getUserContactId());
            userContactDto.setContact(userContact.getContact());
            userContactDto.setContactType(userContact.getContactType());
            userContactDtos.add(userContactDto);
        }
        return userContactDtos;
    }

    private UserProfile retrieveUserProfileOfAuthenticatedUser(HttpServletRequest req) {
        SessionUserDetailsDto details = AuthenticationUtil.getAuthentication(req);
        return userProfileDao.findByUserId(details.getId());
    }
}
