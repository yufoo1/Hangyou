package com.example.hangyou.utils;

import java.sql.*;

public class MysqlConnector {
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://47.104.9.156:3306/hangyou?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
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
        Thread.sleep(1000);
        return conn;
    }
}
