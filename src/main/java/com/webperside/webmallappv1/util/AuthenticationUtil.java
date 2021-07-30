package com.webperside.webmallappv1.util;

import com.webperside.webmallappv1.dto.SessionUserDetails;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationUtil {

    public static boolean isAuthenticated(HttpServletRequest req){
        return req.getSession().getAttribute("loggedUser") != null;
    }

    public static SessionUserDetails getAuthentication(HttpServletRequest req){
        return (SessionUserDetails) req.getSession().getAttribute("loggedUser");
    }
}
