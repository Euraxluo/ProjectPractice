package com.lailelaodi.dao.impl;

import com.lailelaodi.dao.StudentDao;
import com.lailelaodi.pojo.Student;
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
 * @ClassName StudentDaoImpl
 * @Description 这是StudentDao的实现类
 * @Author Euraxluo
 * @Date 18-12-2 下午4:30
 */
public class StudentDaoImpl implements StudentDao {

    QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
    /*
     * @description //TDOD
     * @param 查询所有学生
     * @return java.util.List<Student>
     * @author Euraxluo
     * @date 18-12-2
     */
    @Override
    public List<Student> findAll() throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select * from stu";
        List<Student> list = qr.query(sql,new BeanListHandler<Student>(Student.class));
        return list;

//        try {
//            List<Student> list = qr.query(sql,new MyHandler());
//            System.out.println(list);
//            return list;
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return list;

    }

    @Override
    public List<Student> findStudentByPage(int currentPage) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select * from stu limit ? offset ?";
        List<Student> list = qr.query(sql,new BeanListHandler<Student>(Student.class),PAGE_SIZE,(currentPage-1)*PAGE_SIZE);
        return list;
    }

    @Override
    public void insert(Student student) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "insert into stu value(null,?,?,?,?,?,?)";
        qr.update(sql,student.getSname(),student.getGender(),student.getPhone(),
                student.getBirthday(),student.getHobby(),student.getInfo());
    }

    @Override
    public void delect(int sid) throws SQLException {
      QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
      String sql = "delete from stu where sid=?";
      qr.update(sql,sid);
    }

    @Override
    public Student findStudentById(int sid) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "select * from stu where sid = ?";
        return qr.query(sql,new BeanHandler<Student>(Student.class),sid);
    }

    @Override
    public void update(Student student) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "update stu set sname=? , gender=? , phone=? , birthday=? , hobby=? , info=? where sid = ?";
        qr.update(sql,student.getSname(),student.getGender(),student.getPhone(),
                student.getBirthday(),student.getHobby(),student.getInfo(),student.getSid());
    }

    @Override
    public List<Student> searchStudent(String sname, String sgender) throws SQLException {
        String sql = "select * from stu where 1=1 ";//为了保留where

        List<String> list = new ArrayList<String>();//把参数放在list中

        //判断有没有姓名， 如果有，就组拼到sql语句里面
        if(!TextUtils.isEmpty(sname)){
            sql = sql + "  and sname like ?";
            list.add("%"+sname+"%");
        }

        //判断有没有性别，有的话，就组拼到sql语句里面。
        if(!TextUtils.isEmpty(sgender)){
            sql = sql + " and gender = ?";
            list.add(sgender);
        }

        return qr.query(sql , new BeanListHandler<Student>(Student.class) ,list.toArray() );
    }

    @Override
    public int findcount() throws SQLException {
        String sql = "select COUNT(*) from stu";
        Long result= (Long) qr.query(sql,new ScalarHandler());//用于处理平均值
        return result.intValue();
    }
}
