package com.webperside.webmallappv1.servlets.user;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dto.user.UserContactDto;
import com.webperside.webmallappv1.dto.user.UserProfileDto;
import com.webperside.webmallappv1.service.UserProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name="userProfileServlet",value = "/user-profile/*")
public class UserProfileServlet extends HttpServlet {

    private final UserProfileService userProfileService = ContextLogic.userProfileServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter("msg");
        String code = req.getParameter("code");

        UserProfileDto profile = userProfileService.getUserProfile(req);

        if(profile == null){
            resp.sendRedirect("/index?msg=User not found");
            return;
        }

        req.setAttribute("userProfile",profile);
        req.setAttribute("msg",msg);
        req.setAttribute("code",code);

        req.getRequestDispatcher("/user/user-profile.jsp").forward(req, resp);
    }
}
