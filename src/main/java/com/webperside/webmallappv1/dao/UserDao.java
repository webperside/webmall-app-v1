package com.webperside.webmallappv1.dao;

import com.webperside.webmallappv1.model.User;

public interface UserDao {

    boolean checkUserExistsByUsername(String username);

    int save(User user);

    User findById(Integer id);

    User findByUsernameAndPassword(String username, String password);

    int update(User user);


}
