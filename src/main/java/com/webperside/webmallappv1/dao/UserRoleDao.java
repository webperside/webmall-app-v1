package com.webperside.webmallappv1.dao;

import com.webperside.webmallappv1.model.User;

import java.util.List;

public interface UserRoleDao {

    void save(Integer userId, List<Integer> roleIds);
}
