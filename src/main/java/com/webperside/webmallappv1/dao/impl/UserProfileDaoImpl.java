package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.dao.UserProfileDao;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.enums.Gender;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserProfile;

import java.sql.*;

public class UserProfileDaoImpl extends Connector implements UserProfileDao {

    private UserProfile getUserProfile(ResultSet rs) throws Exception {
        UserProfile us = new UserProfile();
        us.setUserProfileId(rs.getInt(1));
        us.setUser(new User(rs.getInt(2)));
        us.setName(rs.getString(3));
        us.setSurname(rs.getString(4));
        us.setBirthdate(rs.getDate(5).toLocalDate());
        us.setGender(Gender.getByValue(rs.getInt(6)));
        us.setAvatar(rs.getString(7));
        us.setCreatedAt(rs.getTimestamp(8).toInstant());
        Timestamp modifiedAt = rs.getTimestamp(9);
        us.setModifiedAt(modifiedAt != null ? modifiedAt.toInstant() : null);
        us.setDataStatus(DataStatus.ACTIVE);
        return us;
    }

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

    @Override
    public UserProfile findByUserId(Integer userId) {
        try(Connection c = connect()){
            String sql = "select * from user_profile where fk_user_id = ? and data_status = 1";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if(rs.next()){
                return getUserProfile(rs);
            }
            return null;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
