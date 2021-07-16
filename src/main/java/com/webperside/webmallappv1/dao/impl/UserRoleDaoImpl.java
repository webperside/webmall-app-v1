package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.dao.UserRoleDao;
import com.webperside.webmallappv1.enums.DataStatus;

import java.sql.*;
import java.time.Instant;
import java.util.List;

public class UserRoleDaoImpl extends Connector implements UserRoleDao {


    @Override
    public void save(Integer userId, List<Integer> roleIds) {
        try(Connection c = connect()){

            String sql = "insert into user_role(fk_user_id, fk_role_id, created_at, data_status) " +
                    "values (?,?,?,?)";

            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for(Integer roleId : roleIds){
                stmt.setInt(1, userId);
                stmt.setInt(2, roleId);
                stmt.setTimestamp(3, Timestamp.from(Instant.now()));
                stmt.setInt(4, DataStatus.ACTIVE.getValue());

                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
