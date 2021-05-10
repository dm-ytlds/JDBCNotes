package com.dengmin.demi.test;
/*
* 将JDBC所需的所有参数写入配置文件当中。
* */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class Test03 {
    public static void main(String[] args) {
        // 资源绑定器。绑定.properties文件
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        // 提取配置文件中的信息
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        Connection connection = null;
        Statement stmt = null;

        try {
            // 1.注册驱动
            Class.forName(driver);
            // 2.获取连接
            connection = DriverManager.getConnection(url, user, password);
            // 3.获取数据库操作对象
            stmt = connection.createStatement();
            // 4.执行sql语句
            // executeUpdate(insert/delete/update)
            int count = stmt.executeUpdate("delete from dept where deptno=50");
            System.out.println(count == 1 ? "执行成功！ " : "执行失败！ ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
