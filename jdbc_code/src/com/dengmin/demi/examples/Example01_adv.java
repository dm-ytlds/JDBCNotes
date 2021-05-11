package com.dengmin.demi.examples;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
/*
   解决SQL注入问题。
      只要用户提供的信息不参与SQL语句的编译过程，就不会存在这个问题了。
      即使用户提供的信息中含有SQL语句的关键字，但是没有参与编译，对结果没有影响；
      要想用户信息不参与SQL语句的编译，那么必须使用java.sql.PreparedStatement
      PreparedStatement接口继承了java.sql.Statement
      PreparedStatement属于预编译的数据库操作对象。
      PreparedStatement的原理：预先对SQL语句的框架进行编译，然后在给SQL语句赋值。
   对比Statement和PreparedStatement？
      Statement存在sql注入问题，PreparedStatement解决了sql注入问题。
      Statement编译一次执行一次，PreparedStatement编译一次，执行多次。
      PreparedStatement会在编译的时候做类型的安全检查。
   什么时候用Statement？
      当要求使用sql注入的时候，只能用Statement。
 */
public class Example01_adv {
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.注册驱动
            Class.forName(driver);

            // 2.获取连接
            connection = DriverManager.getConnection(url, user, password);

            // 3.创建预编译数据库操作对象
            // 注意：条件中的用户名和密码是接收到的，所以要用到字符串拼接。
            // 先搭一个sql语句的框架。其中?是占位符。注意：占位符不能使用单引号括起来。
            String sql = "select * from t_user where userName= ? and userPwd= ? ";
            ps = connection.prepareStatement(sql);

            // 给sql语句传值。注意：JDBC中下标是从 1 开始的。
            ps.setString(1, userName);
            ps.setString(2, userPwd);
            // 4.执行sql语句
            rs = ps.executeQuery();

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
            if (ps != null) {
                try {
                    ps.close();
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
