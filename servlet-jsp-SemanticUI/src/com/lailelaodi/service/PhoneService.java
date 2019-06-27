package com.lailelaodi.service;

import com.lailelaodi.pojo.PageBean;
import com.lailelaodi.pojo.Phone;

import java.sql.SQLException;
import java.util.List;

/**
 * @InterfaceName PhoneService
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-27 下午10:03
 */
public interface PhoneService {
    Phone findPhoneById(int id) throws SQLException;
    void insert(Phone phone) throws SQLException;
    void delete(int id) throws SQLException;
    void update(Phone phone) throws SQLException;
    PageBean searchPhone(int id,String phoneName, String theme, int nums) throws SQLException;
    PageBean findPhoneByPage(int currentPage) throws SQLException;
}
