package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.dao.RoleDao;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.model.Role;

import java.sql.*;

public class RoleDaoImpl extends Connector implements RoleDao {

    private Role getRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getInt(1));
        role.setRoleName(rs.getString(2));
        role.setCreatedAt(rs.getTimestamp(3).toInstant());

        Timestamp modifiedAt = rs.getTimestamp(4);

        role.setModifiedAt(modifiedAt != null ? modifiedAt.toInstant() : null);
        role.setDataStatus(DataStatus.ACTIVE);
        return role;
    }


    @Override
    public Role findByName(String roleName) {
        try(Connection c = connect()){

            String sql = "select * from role where role_name = ? and data_status = 1";

            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setString(1, roleName);

            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            if(rs.next()){
                return getRole(rs);
            }

            return null;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
