package com.lailelaodi.controller;

import com.lailelaodi.pojo.Student;
import com.lailelaodi.service.StudentService;
import com.lailelaodi.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName EditStudentServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-20 下午7:39
 */
public class EditStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //接受
            int sid = Integer.parseInt(req.getParameter("sid"));
            //查询数据
            StudentService service = new StudentServiceImpl();
            Student stu = service.findStudentById(sid);
            //显示数据
            req.setAttribute("stu",stu);
            req.getRequestDispatcher("studentupdate.jsp").forward(req,resp);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
