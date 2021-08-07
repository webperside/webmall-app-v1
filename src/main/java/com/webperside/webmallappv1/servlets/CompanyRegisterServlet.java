package com.webperside.webmallappv1.servlets;


import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dto.CompanyRegisterDto;
import com.webperside.webmallappv1.dto.SessionUserDetailsDto;
import com.webperside.webmallappv1.service.CompanyService;
import com.webperside.webmallappv1.util.AuthenticationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="companyRegisterServlet", value = "/company-register")
public class CompanyRegisterServlet extends HttpServlet {

    private final CompanyService companyService = ContextLogic.companyServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");

        req.setAttribute("userId",userId);
        req.getRequestDispatcher("/company/company-register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompanyRegisterDto companyRegisterDto = prepareData(req);
        int responseCode = companyService.register(companyRegisterDto);

        resp.sendRedirect("/login");
    }

    private CompanyRegisterDto prepareData(HttpServletRequest req) {
        Integer userId = Integer.parseInt(req.getParameter("userId"));
        String name = req.getParameter("name");
        String description = req.getParameter("desc");
        return new CompanyRegisterDto(userId, name, description);
    }
}
