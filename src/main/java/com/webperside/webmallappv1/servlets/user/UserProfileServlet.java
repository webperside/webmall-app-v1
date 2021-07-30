package com.webperside.webmallappv1.servlets.user;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dto.dto.UserProfileDto;
import com.webperside.webmallappv1.service.UserProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="userProfileServlet",value = "/user-profile/*")
public class UserProfileServlet extends HttpServlet {

    private final UserProfileService userProfileService = ContextLogic.userProfileServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserProfileDto profile = userProfileService.getUserProfile(req);

        if(profile == null){
            resp.sendRedirect("/index?msg=User not found");
            return;
        }

        req.setAttribute("userProfile",profile);

        req.getRequestDispatcher("/user/user-profile.jsp").forward(req, resp);
    }
}
