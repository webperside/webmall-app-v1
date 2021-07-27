package com.webperside.webmallappv1.context;

import com.webperside.webmallappv1.service.CompanyService;
import com.webperside.webmallappv1.service.MailService;
import com.webperside.webmallappv1.service.SecurityService;
import com.webperside.webmallappv1.service.UserService;
import com.webperside.webmallappv1.service.impl.CompanyServiceImpl;
import com.webperside.webmallappv1.service.impl.MailServiceImpl;
import com.webperside.webmallappv1.service.impl.SecurityServiceImpl;
import com.webperside.webmallappv1.service.impl.UserServiceImpl;

public class ContextLogic {

    public static UserService userServiceInstance(){
        return new UserServiceImpl();
    }

    public static MailService mailServiceInstance() { return new MailServiceImpl();}

    public static SecurityService securityServiceInstance() {
        return new SecurityServiceImpl();
    }

    public static CompanyService companyServiceInstance() { return new CompanyServiceImpl();
    }

}
