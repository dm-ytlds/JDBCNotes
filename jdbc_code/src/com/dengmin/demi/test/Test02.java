package com.dengmin.demi.test;

import java.sql.*;

public class Test02 {
    public static void main(String[] args) {

        Connection connection = null;
        Statement stmt = null;
        try {
            // 1.注册驱动方式1
            // Driver driver = new com.mysql.cj.jdbc.Driver();
            // DriverManager.registerDriver(driver);
            // 1.注册驱动方式2：反射机制。是因为源代码中有静态代码块实现了DriverManager.registerDriver()方法。
            // 方式2更常用：因为Class.forName()中的字符串可以写在配置文件中。
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获取连接
            String url = "jdbc:mysql://localhost:3306/testsql";
            String user = "root";
            String password = "1233";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);
            // 3.获取数据库操作对象
            stmt = connection.createStatement();
            // 4.执行sql语句
            String sql = "delete from dept where deptno=50";
            int count = stmt.executeUpdate(sql);
            System.out.println(count == 1 ? "执行成功！" : "执行失败！");
            // 操作结果集

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
