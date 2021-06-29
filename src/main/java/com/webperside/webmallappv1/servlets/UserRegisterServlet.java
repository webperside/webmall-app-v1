package com.webperside.webmallappv1.servlets;

import com.webperside.webmallappv1.dto.UserRegisterDto;
import com.webperside.webmallappv1.enums.Gender;
import com.webperside.webmallappv1.enums.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name="userRegisterServlet",value = "/user-register")
public class UserRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Gender> genders = Arrays.asList(Gender.values());
        List<UserType> userTypes = Arrays.asList(UserType.values());

        req.setAttribute("genders",genders);
        req.setAttribute("userTypes",userTypes);
        req.getRequestDispatcher("user-register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRegisterDto userRegisterDto = prepareData(req);

        System.out.println(userRegisterDto);

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private UserRegisterDto prepareData(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        int gender = Integer.parseInt(req.getParameter("gender"));
        int userType = Integer.parseInt(req.getParameter("userType"));

        return new UserRegisterDto(name, surname, username, password, gender, userType);
    }


}
