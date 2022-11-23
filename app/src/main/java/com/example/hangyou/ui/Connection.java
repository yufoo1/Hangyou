package com.example.hangyou.ui;

import android.util.Log;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static final String TAG = "mysql11111";
    public static void mymysql() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }

                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Log.v(TAG, "加载JDBC驱动成功");
                    } catch (ClassNotFoundException e) {
                        Log.e(TAG, "加载JDBC驱动失败");
                        return;
                    }

                    String ip = "47.104.9.156";
                    int port = 3306;
                    String dbName = "dedeket";
                    String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?userUnicode=true&characterEncoding=utf-8&useSSL=false";
                    String user = "root";
                    String password = "20011206";
                    try {
                        java.sql.Connection conn = (java.sql.Connection)DriverManager.getConnection(url, user, password);
                        Log.d(TAG, "数据库连接成功");
                        conn.close();
                        return;
                    } catch (SQLException e) {
                       Log.e(TAG, e.getMessage());
                    }
                }
            }
        });
        thread.start();
    }
}
