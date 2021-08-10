package com.webperside.webmallappv1.service.impl;

import com.webperside.webmallappv1.context.ContextDao;
import com.webperside.webmallappv1.dao.CompanyDao;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.dao.UserSecurityDao;
import com.webperside.webmallappv1.dto.CompanyRegisterDto;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.model.Company;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserSecurity;
import com.webperside.webmallappv1.service.CompanyService;

import java.time.Instant;

import static com.webperside.webmallappv1.util.MailUtil.sendRegistrationEmail;

public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao = ContextDao.companyDaoInstance();
    private final UserDao userDao = ContextDao.userDaoInstance();

    @Override
    public Integer register(CompanyRegisterDto companyRegisterDto) {
        boolean result = companyDao.checkExistsByName(companyRegisterDto.getName());
        if (result) return -1;
        Company company = saveCompany(companyRegisterDto);
        User user = userDao.findById(companyRegisterDto.getUserId());
        user.setCompany(company);
        userDao.update(user);
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
        company.setName(companyRegister.getName());
        company.setDescription(companyRegister.getDescription());
        company.setCreatedAt(Instant.now());
        company.setDataStatus(DataStatus.ACTIVE);
        return company;
    }
}
