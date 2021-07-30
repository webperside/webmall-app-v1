package com.webperside.webmallappv1.service;

import com.webperside.webmallappv1.dto.dto.UserProfileDto;

import javax.servlet.http.HttpServletRequest;

public interface UserProfileService {

    UserProfileDto getUserProfile(HttpServletRequest req);
}
