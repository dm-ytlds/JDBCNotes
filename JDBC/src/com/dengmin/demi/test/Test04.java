package com.dengmin.demi.test;

import java.sql.*;
import java.util.ResourceBundle;

/*
* 处理结果数据集
* */
public class Test04 {
    public static void main(String[] args) {
        // 资源绑定器。
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        // 获取配置文件中的相关参数信息
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        Connection connection = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            // 1.注册驱动
            Class.forName(driver);
            // 2.获取连接
            connection = DriverManager.getConnection(url, user, password);
            // 3.创建数据库操作对象
            stmt = connection.createStatement();
            // 4.执行sql语句
            // ResultSet executeQuery(select)
            // resultSet = stmt.executeQuery("select deptno,dname,loc from dept");
            // 如果给查询结果更换了别名
            resultSet = stmt.executeQuery("select deptno as '部门编号',dname as '部门名称',loc as '地理位置' from dept");
            // 5.处理结果数据集
            // 遍历取出结果
            /*while (resultSet.next()) {
                // 可以全部用String 类型作为返回类型
                int deptno = resultSet.getInt("deptno");
                String dname = resultSet.getString("dname");
                String loc = resultSet.getString("loc");
                System.out.println(deptno + ", "+ dname + ", " + loc);
            }*/
            // 相应的取出结果列名就得用别名
            while (resultSet.next()) {
                int deptno = resultSet.getInt("部门编号");
                String dname = resultSet.getString("部门名称");
                String loc = resultSet.getString("地理位置");
                System.out.println(deptno + ", "+ dname + ", " + loc);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // 6.释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
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
