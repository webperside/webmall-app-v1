package com.webperside.webmallappv1.servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="companyRegisterServlet", value = "/company-register")
public class CompanyRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");

        req.setAttribute("userId",userId);
        req.getRequestDispatcher("/company/company-register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String desc = req.getParameter("desc");
        String userId = req.getParameter("userId");

        System.out.println(name);
        System.out.println(desc);
        System.out.println(userId);

        //STEP1.    COMPANY MODEL
        //STEP2.    DAO -> SAVE(MODEL)
        //STEP3.    DTO -> PREPARE DATA
        //STEP4.    SERVICE -> SAVE(DTO)
        //STEP4.1   DAO.SAVE()
        //STEP4.2   SEND MAIL
        //STEP5.    CALL SERVICE METHOD

        resp.sendRedirect("/login");
    }
}
