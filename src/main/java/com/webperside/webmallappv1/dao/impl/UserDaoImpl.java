package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.enums.UserStatus;
import com.webperside.webmallappv1.enums.UserType;
import com.webperside.webmallappv1.model.Company;
import com.webperside.webmallappv1.model.User;

import java.sql.*;

public class UserDaoImpl extends Connector implements UserDao {

    private User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt(1));
        user.setCompany(new Company(rs.getInt(2)));
        user.setUsername(rs.getString(3));
        user.setPassword(rs.getString(4));
        user.setUserType(UserType.getByValue(rs.getInt(5)));
        user.setUserStatus(UserStatus.getByValue(rs.getInt(6)));
        user.setCreatedBy(new User(rs.getInt(7)));
        user.setCreatedAt(rs.getTimestamp(8).toInstant());
        user.setModifiedBy(new User(rs.getInt(9)));

        Timestamp modifiedAt = rs.getTimestamp(10);

        user.setModifiedAt(modifiedAt != null ? modifiedAt.toInstant() : null);
        user.setDataStatus(DataStatus.ACTIVE);

        return user;
    }


    @Override
    public boolean checkUserExistsByUsername(String username) {

        try(Connection c = connect()){

            String sql = "select user_id from user where username = ?";

            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1,username);

            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            return rs.next();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public int save(User user) {

        try(Connection c = connect()){

            String sql = "insert into user(username, password, user_type, user_status, created_by, created_at, data_status) " +
                    "values (?,?,?,?,?,?,?)";

            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getUserType().getValue());
            stmt.setInt(4, user.getUserStatus().getValue());

            if(user.getCreatedBy() == null){
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, user.getCreatedBy().getUserId());
            }

            stmt.setTimestamp(6, Timestamp.from(user.getCreatedAt()));
            stmt.setInt(7, DataStatus.ACTIVE.getValue());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next())
            {
                return rs.getInt(1);
            }

            return -1;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    @Override
    public User findById(Integer id) {
        try(Connection c = connect()){

            String sql = "select * from user where user_id = ? and data_status = 1";

            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setInt(1, id);

            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            if(rs.next()){
                return getUser(rs);
            }

            return null;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try(Connection c = connect()){

            String sql = "select * from user where username = ? and password = ? and data_status = 1";

            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2,password);

            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            if(rs.next()){
                return getUser(rs);
            }

            return null;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public int update(User user) {
        try(Connection c = connect()){

            String sql = "update user set password = ? , user_status = ? , modified_by = ? , modified_at = ?, fk_company_id = ? where user_id = ?";

            PreparedStatement stmt = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getPassword());
            stmt.setInt(2, user.getUserStatus().getValue());

            User modifiedBy = user.getModifiedBy();

            if(modifiedBy != null){
                stmt.setInt(3, modifiedBy.getUserId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setTimestamp(4, Timestamp.from(user.getModifiedAt()));
            stmt.setInt(5, user.getUserId());

            Company company = user.getCompany();

            if(company != null){
                stmt.setInt(6, user.getCompany().getCompanyId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            }

            return -1;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
}
