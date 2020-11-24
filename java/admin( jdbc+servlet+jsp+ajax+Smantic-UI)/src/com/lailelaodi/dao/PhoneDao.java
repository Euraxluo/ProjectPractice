package com.lailelaodi.dao;

import com.lailelaodi.pojo.Phone;

import java.sql.SQLException;
import java.util.List;

/**
 * @InterfaceName PhoneDao
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-27 下午10:15
 */
public interface PhoneDao {
    int PAGE_SIZE = 5;
    /*
     * @description //TDOD
     * @param 查询所有学生,返回查询结果
     * @return java.util.List<Phone>
     * @author Euraxluo
     * @date 18-12-2
     */
    List<Phone> findPhoneByPage(int currentPage) throws SQLException;
    Phone findPhoneById(int id) throws SQLException;
    void insert(Phone phone) throws SQLException;
    void delete(int id) throws SQLException;
    void update(Phone phone)throws SQLException;
    List<Phone> searchPhone(int id,String phoneName,String theme,int nums) throws SQLException;
    int findcount() throws SQLException;
}
