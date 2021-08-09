package com.webperside.webmallappv1.context;

import com.webperside.webmallappv1.dao.*;
import com.webperside.webmallappv1.dao.impl.*;

public class ContextDao {

    public static UserDao userDaoInstance(){
        return new UserDaoImpl();
    }

    public static UserProfileDao userProfileDaoInstance(){
        return new UserProfileDaoImpl();
    }

    public static UserSecurityDao userSecurityDaoInstance(){
        return new UserSecurityDaoImpl();
    }

    public static RoleDao roleDaoInstance(){return new RoleDaoImpl();}

    public static UserRoleDao userRoleDaoInstance(){return new UserRoleDaoImpl();
    }

    public static CompanyDao companyDaoInstance() { return new CompanyDaoImpl(); }

    public static UserContactDao userContactDaoInstance() { return new UserContactDaoImpl(); }
}
