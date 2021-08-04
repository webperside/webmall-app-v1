package com.webperside.webmallappv1.servlets.user;

import com.webperside.webmallappv1.dto.dto.UserProfileDto;
import com.webperside.webmallappv1.enums.Gender;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UserProfileEditServlet", value = "/user-profile-edit")
public class UserProfileEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userProfileId = Integer.parseInt(req.getParameter("userProfileId"));
        List<Gender> genders = Arrays.asList(Gender.values());

        req.setAttribute("genders", genders);
        req.setAttribute("userProfileId", userProfileId);

        req.getRequestDispatcher("user-profile-edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private UserProfileDto prepareData(HttpServletRequest req) {
        int userProfileId = Integer.parseInt(req.getParameter("userProfileId"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        LocalDate birthdate = LocalDate.parse(req.getParameter("birthdate"));
        String avatar = req.getParameter("avatar");
        int gender = Integer.parseInt("gender");

    }
}
