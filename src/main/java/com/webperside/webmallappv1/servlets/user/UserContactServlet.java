package com.webperside.webmallappv1.servlets.user;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dto.user.UserContactDto;
import com.webperside.webmallappv1.service.UserContactService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserContactServlet", value = "/user-contact")
public class UserContactServlet extends HttpServlet {

    private final UserContactService userContactService = ContextLogic.userContactServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserContactDto> userContacts = userContactService.getUserContact(req);
        req.setAttribute("userContacts", userContacts);
        req.getRequestDispatcher("").forward(req, resp);
    }
}
