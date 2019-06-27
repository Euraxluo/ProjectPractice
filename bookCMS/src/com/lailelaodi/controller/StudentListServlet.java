package com.lailelaodi.controller;
import com.lailelaodi.pojo.PageBean;
import com.lailelaodi.pojo.Student;
import com.lailelaodi.service.StudentService;
import com.lailelaodi.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName StudentListServlet
 * @Description 负责查询所有学生的信息,然后显示到list.jsp上
 * @Author Euraxluo
 * @Date 18-12-2 下午4:09
 */
public class StudentListServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("currentPage") != null){
            currentPage(req,resp);
        }else {
            findall(req,resp);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("currentPage") != null){
            currentPage(req,resp);
        }else {
            findall(req,resp);
        }
    }

    private void currentPage(HttpServletRequest req, HttpServletResponse resp) {
        try{
            //获取页码
            int currentPage = Integer.parseInt(req.getParameter("currentPage"));
            //根据页码,获取数据
            StudentService service = new StudentServiceImpl();
            PageBean pageBean = service.findStudentByPage(currentPage);

            //加载到作用域
            req.setAttribute("pageBean",pageBean);
            //跳转页面
            req.getRequestDispatcher("studentlist.jsp").forward(req,resp);
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findall(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            //1. 查询出来所有的学生
            StudentService service = new StudentServiceImpl();
            PageBean pageBean = service.findAll();

            //2. 把数据存储到作用域中
            req.setAttribute("pageBean",pageBean);
            //3. 页面跳转
            req.getRequestDispatcher("studentlist.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
