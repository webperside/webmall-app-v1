package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.CompanyDao;
import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.model.Company;

import java.sql.*;

public class CompanyDaoImpl extends Connector implements CompanyDao {

    @Override
    public int save(Company company) {
        try (Connection c = connect()) {
            String sql = "insert into company(fk_user_id, name, description, created_at, data_status) " +
                    "values(?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, company.getUser().getUserId());
            stmt.setString(2, company.getName());
            stmt.setString(3, company.getDescription());
            stmt.setTimestamp(4, Timestamp.from(company.getCreatedAt()));
            stmt.setInt(5, DataStatus.ACTIVE.getValue());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean checkExistsByName(String name) {
        try(Connection c = connect()){
            String sql = "select company_id from company where name = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1,name);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            return rs.next();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
