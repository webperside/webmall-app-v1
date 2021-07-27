package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.dao.UserSecurityDao;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.enums.EmailConfirmation;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserSecurity;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserSecurityDaoImpl extends Connector implements UserSecurityDao {

    private UserSecurity getUserSecurity(ResultSet rs) throws SQLException {
        UserSecurity us = new UserSecurity();
        us.setUserSecurityId(rs.getInt("user_security_id"));
        us.setUser(new User(rs.getInt("fk_user_id")));
        us.setEmailConfirmation(EmailConfirmation.getByValue(rs.getInt("email_confirmation")));
        us.setEmailConfirmationCode(rs.getString("email_confirmation_code"));
        us.setPasswordResetToken(rs.getString("password_reset_token"));

        Timestamp passwordResetTokenExpireDate = rs.getTimestamp("password_reset_token_expire_date");
        LocalDateTime ldt = passwordResetTokenExpireDate == null ?
                null :
                passwordResetTokenExpireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        us.setPasswordResetTokenExpireDate(ldt);
        us.setCreatedAt(rs.getTimestamp("created_at").toInstant());

        Timestamp modifiedAt = rs.getTimestamp("modified_at");

        us.setModifiedAt(modifiedAt != null ? modifiedAt.toInstant() : null);
        us.setDataStatus(DataStatus.ACTIVE);

        return us;
    }

    @Override
    public int save(UserSecurity userSecurity) {
        try(Connection c = connect()){

            String sql = "insert into user_security(fk_user_id, email_confirmation, email_confirmation_code, created_at, data_status) " +
                    "values (?,?,?,?,?)";

            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, userSecurity.getUser().getUserId());
            stmt.setInt(2, userSecurity.getEmailConfirmation().getValue());
            stmt.setString(3, userSecurity.getEmailConfirmationCode());
            stmt.setTimestamp(4, Timestamp.from(userSecurity.getCreatedAt()));
            stmt.setInt(5, userSecurity.getDataStatus().getValue());

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

    @Override
    public UserSecurity findByEmailConfirmationCode(String code) {
        try(Connection c = connect()){

            String sql = "select * from user_security where email_confirmation_code = ? and data_status = 1";

            PreparedStatement stmt = c.prepareStatement(sql);

            stmt.setString(1, code);

            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            if(rs.next()){
                return getUserSecurity(rs);
            }

            return null;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public UserSecurity findByUserId(Integer userId) {
        try(Connection c = connect()){
            String sql = "select * from user_security where fk_user_id = ? and data_status = 1";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            if(rs.next()){
                return getUserSecurity(rs);
            }
            return null;
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(UserSecurity userSecurity) {
        try(Connection c = connect()){

            String sql = "update user_security set email_confirmation = ? , password_reset_token = ? , password_reset_token_expire_date = ? , modified_at = ? " +
                    "where user_security_id = ?";

            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, userSecurity.getEmailConfirmation().getValue());
            stmt.setString(2, userSecurity.getPasswordResetToken());

            LocalDateTime passwordResetTokenExpireDate = userSecurity.getPasswordResetTokenExpireDate();

            stmt.setTimestamp(3, passwordResetTokenExpireDate != null ? Timestamp.valueOf(passwordResetTokenExpireDate) : null);
            stmt.setTimestamp(4, Timestamp.from(userSecurity.getModifiedAt()));
            stmt.setInt(5, userSecurity.getUserSecurityId());

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
