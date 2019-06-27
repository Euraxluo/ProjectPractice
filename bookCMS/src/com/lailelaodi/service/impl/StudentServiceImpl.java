package com.lailelaodi.service.impl;

import com.lailelaodi.dao.StudentDao;
import com.lailelaodi.dao.impl.StudentDaoImpl;
import com.lailelaodi.pojo.PageBean;
import com.lailelaodi.pojo.Student;
import com.lailelaodi.service.StudentService;

import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName StudentServiceImpl
 * @Description 这是学生的业务层的实现类
 * @Author Euraxluo
 * @Date 18-12-2 下午5:13
 */
public class StudentServiceImpl implements StudentService {
    @Override
    public PageBean findAll() throws SQLException {
        PageBean<Student> pageBean = new PageBean<Student>();
        StudentDao dao = new StudentDaoImpl();
        List<Student> list =  dao.findAll();
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public void insert(Student student) throws SQLException {
        StudentDao dao = new StudentDaoImpl();
        dao.insert(student);
    }

    @Override
    public Student findStudentById(int sid) throws SQLException {
        StudentDao dao = new StudentDaoImpl();
        return dao.findStudentById(sid);
    }
    @Override
    public void delect(int sid) throws SQLException{
        StudentDao dao = new StudentDaoImpl();
        dao.delect(sid);
    }

    @Override
    public void update(Student student) throws SQLException {
        StudentDao dao = new StudentDaoImpl();
        dao.update(student);
    }

    @Override
    public PageBean searchStudent(String sname, String sgender) throws SQLException {
        PageBean<Student> pageBean = new PageBean<Student>();
        StudentDao dao = new StudentDaoImpl();
        List<Student> list =  dao.searchStudent(sname,sgender);
        pageBean.setList(list);
        return pageBean;

    }


    @Override
    public PageBean findStudentByPage(int currentPage) throws SQLException {
        PageBean<Student> pageBean = new PageBean<Student>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(StudentDao.PAGE_SIZE);

        StudentDao dao = new StudentDaoImpl();
        List<Student> list = dao.findStudentByPage(currentPage);
        pageBean.setList(list);

        int count = dao.findcount();
        pageBean.setTotalSize(count);
        pageBean.setTotalPage(count % StudentDao.PAGE_SIZE == 0 ? (count / StudentDao.PAGE_SIZE) :(count / StudentDao.PAGE_SIZE + 1));


        return pageBean;
    }
}
