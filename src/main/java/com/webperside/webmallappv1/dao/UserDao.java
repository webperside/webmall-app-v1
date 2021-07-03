package com.webperside.webmallappv1.dao;

import com.webperside.webmallappv1.model.User;

public interface UserDao {

    boolean checkUserExistsByUsername(String username);

    int save(User user);


}
