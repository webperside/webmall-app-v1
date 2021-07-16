package com.webperside.webmallappv1.service;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

    int login(HttpServletRequest req, String username, String password);

    void logout(HttpServletRequest req);

}
