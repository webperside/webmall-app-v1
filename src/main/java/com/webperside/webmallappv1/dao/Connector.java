package com.webperside.webmallappv1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/webmall_v1_v2_db?useSSL=false & serverTimezone=UTC";
        String username = "root";
        String password = "112358";
        return DriverManager.getConnection(url, username, password);
    }

}
