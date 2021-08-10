package com.webperside.webmallappv1.service;

import com.webperside.webmallappv1.model.User;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {

    void setSessionUserDetails(User user);
}
