package com.lailelaodi.dao;

import com.lailelaodi.pojo.Mobielcode;

import java.sql.SQLException;

/**
 * @InterfaceName MobielcodeDao
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-26 下午3:26
 */
public interface MobielcodeDao {
    int checkCode(String username, String password) throws SQLException;
    int insert(Mobielcode mobielcode)throws SQLException;
}
