package com.example.hangyou.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dao {
    public void user_insert(String account, String username, String password, String phone, String gender) throws InterruptedException, SQLException {
        Connection connection = MysqlConnector.getConnection();
        String sql = "INSERT INTO user(account, username, description, phone, gender, password) VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        String description = "这个人很懒，什么都没有留下";
        ps.setString(1, account);
        ps.setString(2, username);
        ps.setString(3, description);
        ps.setString(4, phone);
        ps.setString(5, gender);
        ps.setString(6, password);
        ps.executeUpdate();
    }
}
