package com.webperside.webmallappv1.filters;

import com.webperside.webmallappv1.util.AuthenticationUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(displayName = "securityFilter",filterName = "filter2", urlPatterns = "*")
public class SecurityFilter implements Filter {

    final List<String> authenticatedUrls = Arrays.asList("/","/index", "/user-profile");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String currentUri = req.getRequestURI();

        boolean isLoggedIn = AuthenticationUtil.isAuthenticated(req);

        if(isLoggedIn){ // authenticated
            if(currentUri.contains("login")){
                resp.sendRedirect("/index");
                return;
            }
        } else {
            if(authenticatedUrls.contains(currentUri)){
                resp.sendRedirect(String.format("/login?msg=%s&code=%s","Please login",-1));
                return;
            }
        }

        chain.doFilter(req, resp);

    }
}
