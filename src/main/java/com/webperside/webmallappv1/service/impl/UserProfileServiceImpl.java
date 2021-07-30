package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.dto.SessionUserDetails;
import com.webperside.webmallappv1.dto.dto.UserProfileDto;
import com.webperside.webmallappv1.model.UserProfile;
import com.webperside.webmallappv1.service.UserProfileService;
import com.webperside.webmallappv1.util.AuthenticationUtil;
import com.webperside.webmallappv1.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileDao userProfileDao = ContextDao.userProfileDaoInstance();

    @Override
    public UserProfileDto getUserProfile(HttpServletRequest req) {
        String[] params = req.getRequestURI().split("/");

        UserProfileDto userProfileDto = null;

        if(params.length == 2){
            if(AuthenticationUtil.isAuthenticated(req)){
                SessionUserDetails details = AuthenticationUtil.getAuthentication(req);

                UserProfile userProfile = userProfileDao.findByUserId(details.getId());

                userProfileDto = new UserProfileDto();
                userProfileDto.setAvatar("avatar"); // todo
                userProfileDto.setGender(details.getGender());
                userProfileDto.setFullName(details.getFullName());
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
}
