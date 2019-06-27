package com.lailelaodi.service;

import com.lailelaodi.pojo.PageBean;
import com.lailelaodi.pojo.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * @InterfaceName StudentService 业务处理层
 * @Description 这是学生的业务处理规范
 * @Author Euraxluo
 * @Date 18-12-2 下午5:10
 */
public interface StudentService {
    /*
     * @description //TDOD
     * @param 查询所有学生
     * @return java.util.List<Student>
     * @author Euraxluo
     * @date 18-12-2
     */
    PageBean findAll() throws SQLException;
    Student findStudentById(int sid) throws SQLException;
    void insert(Student student) throws SQLException;
    void delect(int sid) throws SQLException;
    void update(Student student) throws SQLException;
    PageBean searchStudent(String sname,String sgender) throws SQLException;
    PageBean findStudentByPage(int currentPage) throws SQLException;
}
