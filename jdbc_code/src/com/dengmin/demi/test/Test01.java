package com.dengmin.demi.test;

import java.sql.*;

public class Test01 {
    public static void main(String[] args) {
        Connection connection = null;
        Statement stmt = null;
        try {
            // 1.注册驱动
//            Class.forName("com.mysql.jdbc.Driver");
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            // 2.获取连接
            String url = "jdbc:mysql://localhost:3306/testsql";
            String user = "root";
            String password = "1233";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);
            // 3.获取数据库操作对象
            stmt = connection.createStatement();
            // 4.执行sql语句
            String sql = "insert into dept(deptno, dname, loc) values(50, '人事部', '北京')";
            // 返回影响数据库中的记录条数
            int count = stmt.executeUpdate(sql);
            System.out.println(count == 1 ? "执行成功！" : "执行失败！");
            // 5.处理查询结果集
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // 6.释放资源
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
