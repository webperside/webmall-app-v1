package com.webperside.webmallappv1.servlets;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    private final SecurityService securityService = ContextLogic.securityServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String msg = req.getParameter("msg");
        String code = req.getParameter("code");

        req.setAttribute("msg",msg);
        req.setAttribute("code",code);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String date = req.getParameter("trip-start");
        System.out.println(date); //
        int responseCode = securityService.login(username, password);
        String url;
        if (responseCode == -1) {
            url = String.format("/login?msg=%s&code=%s", "Username or passwor incorrect", responseCode);
        } else if (responseCode == 0) {
            url = String.format("/login?msg=%s&code=%s", "Your account is deactive", responseCode);
        } else if (responseCode == 2) {
            url = String.format("/login?msg=%s&code=%s", "Please click the link on your email to activate your account.", responseCode);
        } else if (responseCode == 3) {
            url = String.format("/login?msg=%s&code=%s", "Your account is blocked", responseCode);
        } else {
            url = "/index";
        }
        resp.sendRedirect(url);
    }
}
