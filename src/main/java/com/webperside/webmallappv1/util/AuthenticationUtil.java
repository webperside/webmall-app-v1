package com.webperside.webmallappv1.util;

import com.webperside.webmallappv1.dto.SessionUserDetailsDto;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationUtil {

    // shared HttpServletRequest resource

    public static boolean isAuthenticated(HttpServletRequest req){
        return req.getSession().getAttribute("loggedUser") != null;
    }

    public static SessionUserDetailsDto getAuthentication(HttpServletRequest req){
        return (SessionUserDetailsDto) req.getSession().getAttribute("loggedUser");
    }
}
