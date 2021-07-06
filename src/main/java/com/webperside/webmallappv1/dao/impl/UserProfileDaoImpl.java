package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.model.UserProfile;

import java.sql.*;

public class UserProfileDaoImpl extends Connector implements UserProfileDao {
    @Override
    public int save(UserProfile userProfile) {
        try(Connection c = connect()){

            String sql = "insert into user_profile(fk_user_id, name, surname, gender, created_at, data_status) " +
                    "values (?,?,?,?,?,?)";

            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, userProfile.getUser().getUserId());
            stmt.setString(2, userProfile.getName());
            stmt.setString(3, userProfile.getSurname());
            stmt.setInt(4, userProfile.getGender().getValue());
            stmt.setTimestamp(5, Timestamp.from(userProfile.getCreatedAt()));
            stmt.setInt(6, userProfile.getDataStatus().getValue());

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
