package com.webperside.webmallappv1.service;

import com.webperside.webmallappv1.dto.user.UserProfileDto;
import com.webperside.webmallappv1.dto.user.UserProfileEditDto;

import javax.servlet.http.HttpServletRequest;

public interface UserProfileService {

    UserProfileDto getUserProfile(HttpServletRequest req);

    UserProfileEditDto getUserProfileForEdit(HttpServletRequest req);

    int update(HttpServletRequest req, UserProfileEditDto editDto);
}
