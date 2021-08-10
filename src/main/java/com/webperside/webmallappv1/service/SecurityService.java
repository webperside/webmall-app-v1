package com.webperside.webmallappv1.service;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

    int login(String username, String password);

    void logout(HttpServletRequest req);

}
