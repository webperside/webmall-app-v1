package com.webperside.webmallappv1.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="userRegisterServlet",value = "/user-confirm")
public class UserConfirmServlet extends HttpServlet {

    // /user-confirm?code=32487634

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        //todo
    }
}
