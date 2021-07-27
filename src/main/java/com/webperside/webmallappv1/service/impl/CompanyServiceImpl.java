package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.context.ContextLogic;
import com.webperside.webmallappv1.dao.CompanyDao;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dao.UserSecurityDao;
import com.webperside.webmallappv1.dto.CompanyRegisterDto;
import com.webperside.webmallappv1.dto.MailDto;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.model.Company;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserSecurity;
import com.webperside.webmallappv1.service.CompanyService;
import com.webperside.webmallappv1.service.MailService;

import java.time.Instant;

public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao = ContextDao.companyDaoInstance();
    private final UserDao userDao = ContextDao.userDaoInstance();
    private final UserSecurityDao userSecurityDao = ContextDao.userSecurityDaoInstance();
    private final MailService mailService = ContextLogic.mailServiceInstance();

    @Override
    public Integer register(CompanyRegisterDto companyRegisterDto) {
        boolean result = companyDao.checkExistsByName(companyRegisterDto.getName());
        if (result) return -1;
        Company company = saveCompany(companyRegisterDto);
        User user = userDao.findById(companyRegisterDto.getUserId());
        UserSecurity userSecurity = userSecurityDao.findByUserId(user.getUserId());
        sendRegistrationEmail(user.getUsername(), userSecurity.getEmailConfirmationCode());
        return company.getCompanyId();
    }

    private Company saveCompany(CompanyRegisterDto companyRegisterDto) {
        Company company = prepareCompany(companyRegisterDto);
        int companyId = companyDao.save(company);
        company.setCompanyId(companyId);
        return company;
    }

    private Company prepareCompany(CompanyRegisterDto companyRegister) {
        Company company = new Company();
        company.setUser(new User(companyRegister.getUserId()));
        company.setName(companyRegister.getName());
        company.setDescription(companyRegister.getDescription());
        company.setCreatedAt(Instant.now());
        company.setDataStatus(DataStatus.ACTIVE);
        return company;
    }

    private void sendRegistrationEmail(String to, String code){
        String url = "http://localhost:8080/confirm-registration?code="+code;
        MailDto mail = new MailDto();
        mail.setTo(to);
        mail.setSubject("Confirm Registration");
//        mail.setBody("Use this link to confirm your profile : [LINK]".replaceAll("[LINK]",url));
        mail.setBody("Use this link to confirm your profile : " + url);
        mailService.send(mail);
    }
}
