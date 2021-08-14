package com.webperside.webmallappv1.dao.impl;

import com.webperside.webmallappv1.dao.Connector;
import com.webperside.webmallappv1.dao.UserContactDao;
import com.webperside.webmallappv1.enums.ContactType;
import com.webperside.webmallappv1.enums.DataStatus;
import com.webperside.webmallappv1.model.User;
import com.webperside.webmallappv1.model.UserContact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserContactDaoImpl extends Connector implements UserContactDao {

    private UserContact getUserContact(ResultSet rs) throws Exception {
        UserContact uc = new UserContact();
        uc.setUserContactId(rs.getInt(1));
        uc.setUser(new User(rs.getInt(2)));
        uc.setContact(rs.getString(3));
        uc.setContactType(ContactType.getByValue(rs.getInt(4)));
        uc.setCreatedAt(rs.getTimestamp(5).toInstant());
        Timestamp modifiedAt = rs.getTimestamp(6);
        uc.setModifiedAt(modifiedAt != null ? modifiedAt.toInstant() : null);
        uc.setDataStatus(DataStatus.ACTIVE);
        return uc;
    }

    @Override
    public List<UserContact> getUserContacts(Integer userId) {
        List<UserContact> userContacts = new ArrayList<>();

        try (Connection c = connect()) {
            String sql = "select * from user_contact where fk_user_id = ? and data_status = 1";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                userContacts.add(getUserContact(rs));
            }
            return userContacts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(List<UserContact> userContacts) {
        try (Connection c = connect()){
            String sql = "insert into user_contact(fk_user_id, contact, contact_type, created_at, data_status) " +
                    "values(?,?,?,?,?)";
            PreparedStatement stmt = c.prepareStatement(sql);

            for(UserContact userContact : userContacts) {
                stmt.setInt(1, userContact.getUser().getUserId());
                stmt.setString(2, userContact.getContact());
                stmt.setInt(3, userContact.getContactType().getValue());
                stmt.setTimestamp(4, Timestamp.from(userContact.getCreatedAt()));
                stmt.setInt(5, DataStatus.ACTIVE.getValue());
                stmt.addBatch();
            }

            stmt.executeBatch();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(List<UserContact> userContacts) {
        try (Connection c = connect()){
            for(UserContact userContact : userContacts) {
                String sql = "update user_contact set contact = ?, modified_at = ? where fk_user_id = ? and contact_type = ?";
                PreparedStatement stmt = c.prepareStatement(sql);

                stmt.setString(1, userContact.getContact());
                stmt.setTimestamp(2, Timestamp.from(userContact.getModifiedAt()));
                stmt.setInt(3, userContact.getUser().getUserId());
                stmt.setInt(4, userContact.getContactType().getValue());

                stmt.execute();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
System.out.println("start");
            System.out.println(userContacts);



 */
}
