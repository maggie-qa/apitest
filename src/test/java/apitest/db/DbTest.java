package apitest.db;

import com.alibaba.fastjson.JSON;
import com.github.checkpoint.CheckPointUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://118.24.13.38:3308/goods?characterEncoding=utf8&useSSL=false";

        List<DbUser> dbUsers = getDbUser();
        // 将List对象转为json
        String jsonString = JSON.toJSONString(dbUsers);
//        System.out.println(jsonString);
        System.out.println(CheckPointUtils.check(jsonString,"size>1").getMsg());

    }

    // 数据库数据封装
    public static List<DbUser> getDbUser() {
        List<DbUser> dbUsers = new ArrayList<DbUser>();
        Connection conn = null;
        Statement statement = null;
        String sql = "select * from t_user_test";
        // 加载数据库驱动类--mysql
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 创建数据库链接
            conn = DriverManager.getConnection("jdbc:mysql://118.24.13.38:3308/goods?characterEncoding=utf8&useSSL=false", "zhangsan", "123123");
            // 创建执行对象
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                DbUser dbUser = new DbUser();
                dbUser.setUid(resultSet.getString(1));
                dbUser.setLoginname(resultSet.getString(2));
                dbUser.setLoginpass(resultSet.getString(3));
                dbUsers.add(dbUser);
            }
            return dbUsers;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭数据库链接
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

}
