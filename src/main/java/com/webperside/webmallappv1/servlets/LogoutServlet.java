package com.webperside.webmallappv1.servlets;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    private final SecurityService securityService = ContextLogic.securityServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        securityService.logout(req);
        resp.sendRedirect("/login");
    }
}
