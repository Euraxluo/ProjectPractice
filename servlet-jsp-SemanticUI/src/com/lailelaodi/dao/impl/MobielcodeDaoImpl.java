package com.lailelaodi.dao.impl;

import com.lailelaodi.dao.MobielcodeDao;
import com.lailelaodi.pojo.Mobielcode;
import com.lailelaodi.util.JDBCUtil;
import com.lailelaodi.util.TextUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * @ClassName MobielcodeImpl
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-26 下午3:28
 */
public class MobielcodeDaoImpl implements MobielcodeDao {

    @Override
    public int checkCode(String phone,String code) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select count(*) from mobielcode where phone =? and code =? and createTime >= DATE_SUB(?,INTERVAL 10 MINUTE)";
        Long result= (Long) qr.query(sql,new ScalarHandler(),phone,code,TextUtils.now());
        return result.intValue();
    }

    @Override
    public int insert(Mobielcode mobielcode) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
//        String sql = "insert into mobielcode (id,phone,code,createtime)values(null,?,?,?)" +
//                "ON DUPLICATE KEY UPDATE createtime=?";
        String sql = "replace into mobielcode set phone=?,code=?,createTime=?";
        try {
            qr.update(sql,mobielcode.getPhone(),mobielcode.getCode(),TextUtils.now());
            return TextUtils.SECCESS;
        } catch (SQLException e) {
            return TextUtils.FAIL;
        }
    }
}
