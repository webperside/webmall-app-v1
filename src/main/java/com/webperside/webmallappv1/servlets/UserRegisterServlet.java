package com.webperside.webmallappv1.servlets;

import com.webperside.webmallappv1.dto.UserRegisterDto;
import com.webperside.webmallappv1.enums.Gender;
import com.webperside.webmallappv1.enums.UserType;
import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.service.UserService;

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

    private final UserService userService = ContextLogic.userServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Gender> genders = Arrays.asList(Gender.values());
        List<UserType> userTypes = Arrays.asList(UserType.values());

        req.setAttribute("genders",genders);
        req.setAttribute("userTypes",userTypes);
        req.getRequestDispatcher("/user/user-register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRegisterDto userRegisterDto = prepareData(req);

        int responseCode = userService.register(userRegisterDto);

        sendRedirectByResponseCode(responseCode, resp);

//        resp.sendRedirect("/login");
    }

    private void sendRedirectByResponseCode(int responseCode, HttpServletResponse resp) throws IOException {
        String url;
        if(responseCode == -1){
            url = ""; // todo task4
        } else if(responseCode == 0) {
            url = "/login";
        } else {
            url = "/company-register?userId="+responseCode;
        }

        resp.sendRedirect(url);
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
