package com.webperside.webmallappv1.servlets.user;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.service.UserProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "userAvatarServlet", value = "/user-avatar")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class UserAvatarServlet extends HttpServlet {

    private final UserProfileService userProfileService = ContextLogic.userProfileServiceInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("file");
        userProfileService.setAvatar(part);
        resp.sendRedirect("/user-profile");
    }
}
