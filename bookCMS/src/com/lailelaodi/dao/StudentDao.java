package com.lailelaodi.dao;

import com.lailelaodi.pojo.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * @InterfaceName StudentDao
 * @Description 针对学生表的数据访问
 * @Author Euraxluo
 * @Date 18-12-2 下午4:18
 */
public interface StudentDao {
    int PAGE_SIZE = 5;
    /*
     * @description //TDOD
     * @param 查询所有学生,返回查询结果
     * @return java.util.List<Student>
     * @author Euraxluo
     * @date 18-12-2
     */
    List<Student> findStudentByPage(int currentPage) throws SQLException;
    List<Student> findAll() throws SQLException;
    Student findStudentById(int sid) throws SQLException;
    void insert(Student student) throws SQLException;
    void delect(int sid) throws SQLException;
    void update(Student student)throws SQLException;
    List<Student> searchStudent(String sname,String sgender) throws SQLException;
    int findcount() throws SQLException;
}
