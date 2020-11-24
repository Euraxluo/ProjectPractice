package com.lailelaodi.dao.impl;

import com.lailelaodi.dao.UserDao;
import com.lailelaodi.pojo.User;
import com.lailelaodi.util.JDBCUtil;
import com.lailelaodi.util.TextUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * @ClassName UserDaoImpl
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-25 上午9:21
 */
public class UserDaoImpl implements UserDao {
    @Override
    public int insert(User user){
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "insert into user (userid,username,password," +
                "phone,email,createtime,updatetime)values(null,?,?,?,?,?,?)";
        try {
            qr.update(sql,user.getUsername(),user.getPassword(),user.getPhone(),user.getEmail(), TextUtils.now(),TextUtils.now());
            return TextUtils.SECCESS;
        } catch (SQLException e) {
            return TextUtils.FAIL;
        }
    }

    @Override
    public User selectLogin(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select * from user where username =? and password =?";
        return qr.query(sql,new BeanHandler<User>(User.class),username,password);
    }

    @Override
    public int checkUsername(String username) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select count(1) from user where username =?";
        Long result= (Long) qr.query(sql,new ScalarHandler(),username);//用于处理平均值
        return result.intValue();
    }

    @Override
    public int checkEmail(String email) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select count(1) from user where email =?";
        Long result= (Long) qr.query(sql,new ScalarHandler(),email);//用于处理平均值
        return result.intValue();
    }

    @Override
    public int checkPhone(String phone) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select count(1) from user where phone =?";
        Long result= (Long) qr.query(sql,new ScalarHandler(),phone);//用于处理平均值
        return result.intValue();
    }
}
