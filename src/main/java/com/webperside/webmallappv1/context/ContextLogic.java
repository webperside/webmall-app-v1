package com.webperside.webmallappv1.context;

import com.webperside.webmallappv1.service.*;
import com.webperside.webmallappv1.service.impl.*;

public class ContextLogic {

    public static UserService userServiceInstance(){
        return new UserServiceImpl();
    }

    public static MailService mailServiceInstance() { return new MailServiceImpl();}

    public static SecurityService securityServiceInstance() {
        return new SecurityServiceImpl();
    }

    public static CompanyService companyServiceInstance(){ return new CompanyServiceImpl();}

    public static UserProfileService userProfileServiceInstance(){return new UserProfileServiceImpl();};

    public static SessionService sessionServiceInstance(){return new SessionServiceImpl();}

}
