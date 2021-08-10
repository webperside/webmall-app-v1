package com.webperside.webmallappv1.filters;

import com.webperside.webmallappv1.util.HttpUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(displayName = "resourceFilter",filterName = "filter0", urlPatterns = "*")
public class ResourceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpUtil.REQUEST = (HttpServletRequest) request;
        chain.doFilter(request, response);
    }
}
