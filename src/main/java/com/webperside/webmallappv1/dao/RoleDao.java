package com.webperside.webmallappv1.dao;

import com.webperside.webmallappv1.model.Role;

public interface RoleDao {

    Role findByName(String roleName);
}
