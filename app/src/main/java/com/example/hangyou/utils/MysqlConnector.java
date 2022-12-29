package com.example.hangyou.utils;

import java.sql.*;

public class MysqlConnector {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://47.104.9.156:3306/hangyou?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "20011206";
    static Connection conn = null;
    public static Connection getConnection() throws InterruptedException {
        new Thread(() -> {
            try {
                Class.forName(JDBC_DRIVER);
                try {
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1500);
        return conn;
    }
}
