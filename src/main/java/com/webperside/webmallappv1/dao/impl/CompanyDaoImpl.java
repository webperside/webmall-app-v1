package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.CompanyDao;
import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.model.Company;
import com.webperside.webmallappv1.model.User;

import java.sql.*;

public class CompanyDaoImpl extends Connector implements CompanyDao {

    private Company getCompany(ResultSet rs) throws SQLException {
        Company company = new Company();
        company.setCompanyId(rs.getInt(1));
        company.setUser(new User(rs.getInt(2)));
        company.setName(rs.getString(3));
        company.setDescription(rs.getString(4));
        company.setLogo(rs.getString(5));
        company.setCreatedAt(rs.getTimestamp(6).toInstant());

        Timestamp modifiedAt = rs.getTimestamp(7);

        company.setModifiedAt(modifiedAt != null ? modifiedAt.toInstant() : null);
        company.setDataStatus(DataStatus.ACTIVE);
        return company;
    }

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
            String sql = "select company_id from company where name = ? and data_status = 1";
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

    @Override
    public Company findByUserId(Integer userId) {
        try(Connection c = connect()){
            String sql = "select * from company where fk_user_id = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1,userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if(rs.next()){
                return getCompany(rs);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
