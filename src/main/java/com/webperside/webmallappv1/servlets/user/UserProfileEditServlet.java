package com.webperside.webmallappv1.servlets.user;

import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dto.user.UserProfileDto;
import com.webperside.webmallappv1.dto.user.UserProfileEditDto;
import com.webperside.webmallappv1.enums.Gender;
import com.webperside.webmallappv1.service.UserProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "UserProfileEditServlet", value = "/user-profile-edit")
public class UserProfileEditServlet extends HttpServlet {

    private final UserProfileService userProfileService = ContextLogic.userProfileServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfileEditDto userProfile = userProfileService.getUserProfileForEdit(req);
        List<Gender> genders = Arrays.asList(Gender.values());

        req.setAttribute("genders", genders);
        req.setAttribute("userProfile",userProfile);

        req.getRequestDispatcher("/user/user-profile-edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfileEditDto editDto = prepareData(req);
        int responseCode = userProfileService.update(req, editDto);

        resp.sendRedirect("/user-profile?msg=Successfully updated!&code="+responseCode);
    }

    private UserProfileEditDto prepareData(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String birthdate = req.getParameter("birthdate");
        byte gender = Byte.parseByte(req.getParameter("gender"));
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        return new UserProfileEditDto(id, name, surname, gender, birthdate, phone, email, address);
    }
}
