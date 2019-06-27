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
 * @ClassName SearchStudentServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-21 上午9:00
 */
public class SearchStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            //取值
            String sname = req.getParameter("sname");
            String sgender = req.getParameter("sgender");
            //找service查询
            StudentService service =new StudentServiceImpl();
            PageBean pageBean = service.searchStudent(sname,sgender);
            //存储在作用域中
            req.setAttribute("pageBean", pageBean);
            //跳转,直接跳转
            req.getRequestDispatcher("studentlist.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
