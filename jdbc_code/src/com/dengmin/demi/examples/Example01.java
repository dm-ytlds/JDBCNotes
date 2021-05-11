package com.dengmin.demi.examples;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/*
* 模拟用户登录功能的实现：
* 业务描述：
*   用户手动输入用户名和密码；
*   Java程序连接数据库验证用户名和密码是否合法；
*   给出是否合法判定的结果：合法：登录成功！；不合法：登录失败！
* 数据的准备：
*   用PowerDesigner工具进行数据库表的设计。
* 程序出现的问题：
*   用户名：abc
*   密码：abc' or '1'='1
*   登录成功
*   这种现象叫做SQL注入。
* */
public class Example01 {
    public static void main(String[] args) {
        // 登录初始化
        Map<String, String> loginInfo = initUI();
        // 验证用户名和密码
        boolean loginSuccess = login(loginInfo);
        System.out.println(loginSuccess ? "登录成功！" : "登录失败！");
    }

    /**
     * 登录初始化
     * @return 用户输入的用户名和密码
     */
    private static Map<String, String> initUI() {
        // 用Map集合接收用户输入信息
        Map<String, String> loginInfo = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print("用户名：");
        String userName = scanner.nextLine();
        System.out.print("密码：");
        String password = scanner.nextLine();

        loginInfo.put("userName", userName);
        loginInfo.put("password", password);
        return loginInfo;
    }

    /**
     * 登录验证
     * @param loginInfo 用户输入的用户名和密码信息
     * @return 是否登录成功
     */
    private static boolean login(Map<String, String> loginInfo) {
        // 资源绑定器。
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        // 读取.properties文件中的数据
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        // 将用户输入的用户名和密码提取出来
        String userName = loginInfo.get("userName");
        String userPwd = loginInfo.get("password");

        // 打标记的意识
        boolean loginSuccess = false;

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 1.注册驱动
            Class.forName(driver);
            // 2.获取连接
            connection = DriverManager.getConnection(url, user, password);
            // 3.创建数据库操作对象
            stmt = connection.createStatement();
            // 4.执行sql语句
            // 注意：条件中的用户名和密码是接收到的，所以要用到字符串拼接。
            String sql = "select * from t_user where userName='"+ userName +"'and userPwd='" + userPwd +"'";
            /*
            * sql注入出现的位置。
            * 当sql语句完成拼接后，发送sql 语句给DBMS,DBMS进行sql编译。
            * 正好将用户提供的非法信息编译进去，导致了原sql语句的含义被扭曲了。
            * */
            rs = stmt.executeQuery(sql);
            //5.处理结果集
            if(rs.next()) {
                // 登录成功
                loginSuccess = true;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // 6.释放资源
            if (rs != null) {
                try {
                    rs.close();
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
        return loginSuccess;
    }
}
