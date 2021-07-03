package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.dao.UserDao;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.model.User;

import java.sql.*;

public class UserDaoImpl extends Connector implements UserDao {
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
}
