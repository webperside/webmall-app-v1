package com.webperside.webmallappv1.servlets;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="userConfirmServlet",value = "/confirm-registration")
public class UserConfirmServlet extends HttpServlet {

    // /user-confirm?code=32487634

    private final UserService userService = ContextLogic.userServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");

        int responseCode = userService.confirmUser(code);

        sendRedirectByResponseCode(responseCode, resp);
    }

    private void sendRedirectByResponseCode(int responseCode, HttpServletResponse resp) throws IOException {
        String url = "/login?msg=%s&code=%s";
        String msg;
        if(responseCode == -1){
            msg = "Confirmation code not found";
        } else {
            msg = "Your account is confirmed";
        }

        resp.sendRedirect(String.format(url, msg, responseCode));
    }
}
