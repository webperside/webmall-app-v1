package com.webperside.webmallappv1.servlets.company.admin;

import com.webperside.webmallappv1.dto.SessionUserDetailsDto;
import com.webperside.webmallappv1.util.AuthenticationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="companyAdminServlet", value = "/company/admin/")
public class CompanyAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionUserDetailsDto details = AuthenticationUtil.getAuthentication(req);

        req.setAttribute("companyName",details.getCompanyName());
        req.getRequestDispatcher("/company-admin-panel/index.jsp").forward(req, resp);
    }
}
