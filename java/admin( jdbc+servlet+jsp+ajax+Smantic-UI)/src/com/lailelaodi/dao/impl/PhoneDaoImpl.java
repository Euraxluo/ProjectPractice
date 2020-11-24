package com.lailelaodi.dao.impl;

import com.lailelaodi.dao.PhoneDao;
import com.lailelaodi.pojo.Phone;
import com.lailelaodi.util.JDBCUtil;
import com.lailelaodi.util.TextUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PhoneDaoImpl
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-27 下午10:16
 */
public class PhoneDaoImpl implements PhoneDao {

    @Override
    public List<Phone> findPhoneByPage(int currentPage) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select * from phone limit ? offset ?";
        List<Phone> list = qr.query(sql,new BeanListHandler<Phone>(Phone.class),PAGE_SIZE,(currentPage-1)*PAGE_SIZE);
        return list;
    }

    @Override
    public Phone findPhoneById(int id) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select * from phone where id = ?";
        return qr.query(sql,new BeanHandler<Phone>(Phone.class),id);
    }

    @Override
    public void insert(Phone phone) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "insert into phone value(null,?,?,?,?,?,?,?)";
        qr.update(sql,phone.getPhoneName(),phone.getTheme(),phone.getUrl(),phone.getNums(),0,TextUtils.now(),0);

    }

    @Override
    public void delete(int id) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "delete from phone where id=?";
        qr.update(sql,id);
    }

    @Override
    public void update(Phone phone) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "update phone set phoneName=? , theme=? , url=? , nums=? , isShow=? , isdelte=? where sid = ?";
        qr.update(sql,phone.getPhoneName(),phone.getTheme(),phone.getUrl(),phone.getNums(),phone.getIsShow(),phone.getIsdelte(),phone.getId());

    }

    @Override
    public List<Phone> searchPhone(int id,String phoneName, String theme, int nums) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select * from phone where 1=1 ";//为了保留where

        List<String> list = new ArrayList<String>();//把参数放在list中

        if(id>=0){
            sql = sql + " and id = ?";
            list.add(String.valueOf(nums));
        }

        //判断有没有姓名， 如果有，就组拼到sql语句里面
        if(!TextUtils.isEmpty(phoneName)){
            sql = sql + "  and phoneName like ?";
            list.add("%"+phoneName+"%");
        }

        //判断有没有性别，有的话，就组拼到sql语句里面。
        if(!TextUtils.isEmpty(theme)){
            sql = sql + " and theme = ?";
            list.add("%"+theme+"%");
        }

        if(nums>=0){
            sql = sql + " and nums = ?";
            list.add(String.valueOf(nums));
        }

        return qr.query(sql , new BeanListHandler<Phone>(Phone.class) ,list.toArray() );
    }

    @Override
    public int findcount() throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select COUNT(*) from phone";
        Long result= (Long) qr.query(sql,new ScalarHandler());//用于处理平均值
        return result.intValue();
    }
}
