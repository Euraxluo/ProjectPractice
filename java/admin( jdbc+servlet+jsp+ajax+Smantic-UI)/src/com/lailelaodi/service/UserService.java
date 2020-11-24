package com.lailelaodi.service;

import com.lailelaodi.pojo.Mobielcode;
import com.lailelaodi.pojo.ReturnMessage;
import com.lailelaodi.pojo.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @InterfaceName UserService
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-24 下午11:23
 */
public interface UserService {
    ReturnMessage login(String username, String password) throws SQLException;
    ReturnMessage register(User user) throws SQLException;
    ReturnMessage regiscode(Mobielcode mobielcode)throws SQLException;
    ReturnMessage checkValid(String str, String type) throws SQLException;
}
