package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.dto.SessionUserDetailsDto;
import com.webperside.webmallappv1.dto.user.UserProfileDto;
import com.webperside.webmallappv1.dto.user.UserProfileEditDto;
import com.webperside.webmallappv1.enums.Gender;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.service.SessionService;
import com.webperside.webmallappv1.service.UserProfileService;
import com.webperside.webmallappv1.util.AuthenticationUtil;
import com.webperside.webmallappv1.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileDao userProfileDao = ContextDao.userProfileDaoInstance();
    private final UserDao userDao = ContextDao.userDaoInstance();
    private final SessionService sessionService = ContextLogic.sessionServiceInstance();

    @Override
    public UserProfileDto getUserProfile(HttpServletRequest req) {
        String[] params = req.getRequestURI().split("/");

        UserProfileDto userProfileDto = null;

        if(params.length == 2){
            if(AuthenticationUtil.isAuthenticated(req)){
                UserProfile userProfile = retrieveUserProfileOfAuthenticatedUser(req);

                userProfileDto = new UserProfileDto();
                userProfileDto.setAvatar("avatar"); // todo
                userProfileDto.setGender(userProfile.getGender().getName());
                userProfileDto.setFullName(userProfile.getName() + " " + userProfile.getSurname());
                userProfileDto.setBirthDate(userProfile.getBirthdate());
                userProfileDto.setMe(true);
            }
        } else if(params.length == 3 && StringUtils.isNumeric(params[2])){
            Integer id = Integer.parseInt(params[2]);

            UserProfile userProfile = userProfileDao.findByUserId(id);

            if(userProfile != null){

                userProfileDto = new UserProfileDto();
                userProfileDto.setAvatar("avatar"); // todo
                userProfileDto.setGender(userProfile.getGender().getName());
                userProfileDto.setFullName(userProfile.getName() + " " + userProfile.getSurname());
                userProfileDto.setBirthDate(userProfile.getBirthdate());
                userProfileDto.setMe(false);
            }
        }

        return userProfileDto;
    }

    @Override
    public UserProfileEditDto getUserProfileForEdit(HttpServletRequest req) {
        UserProfile userProfile = retrieveUserProfileOfAuthenticatedUser(req);

        UserProfileEditDto edit = new UserProfileEditDto();
        edit.setId(userProfile.getUserProfileId());
        edit.setGender(userProfile.getGender().getValue());
        edit.setName(userProfile.getName());
        edit.setSurname(userProfile.getSurname());

        LocalDate birthdate = userProfile.getBirthdate();

        edit.setBirthdate(birthdate != null ? birthdate.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        ) : null);

        return edit;
    }

    @Override
    public int update(HttpServletRequest req, UserProfileEditDto editDto) {
        UserProfile userProfile = userProfileDao.findById(editDto.getId());

        userProfile.setName(editDto.getName());
        userProfile.setSurname(editDto.getSurname());
        userProfile.setGender(Gender.getByValue(editDto.getGender()));

        String birthdate = editDto.getBirthdate();

        userProfile.setBirthdate(birthdate != null ? LocalDate.parse(
                birthdate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        ) : null);

        userProfile.setModifiedAt(Instant.now());

        int code = userProfileDao.update(userProfile);

        if(code > 0){
            User user = userDao.findById(userProfile.getUser().getUserId());
            sessionService.setSessionUserDetails(req, user);
        }

        return code;
    }

    private UserProfile retrieveUserProfileOfAuthenticatedUser(HttpServletRequest req){
        SessionUserDetailsDto details = AuthenticationUtil.getAuthentication(req);
        return userProfileDao.findByUserId(details.getId());
    }
}
