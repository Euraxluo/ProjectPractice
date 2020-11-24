package com.lailelaodi.dao;

import com.lailelaodi.pojo.User;

import java.sql.SQLException;

/**
 * @InterfaceName UserDao
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-25 上午9:00
 */
public interface UserDao {
    User selectLogin(String username, String password) throws SQLException;
    int checkUsername(String username) throws SQLException;
    int checkEmail(String email) throws SQLException;
    int checkPhone(String phone) throws SQLException;
    int insert(User user)throws SQLException;
}
