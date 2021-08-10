package com.webperside.webmallappv1.dao;

import com.webperside.webmallappv1.model.Company;

public interface CompanyDao {

    int save(Company company);

    boolean checkExistsByName(String name);

    Company findById(Integer userId);

}
