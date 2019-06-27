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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName UpdateStudentServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-20 下午8:54
 */
public class UpdateStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try{
            int sid = Integer.parseInt(req.getParameter("sid"));
            String sname = req.getParameter("sname");
            String gender = req.getParameter("gender");
            String phone = req.getParameter("phone");
            String birthday = req.getParameter("birthday");
            String info = req.getParameter("info");
            String [] h  = req.getParameterValues("hobby");
            String hobby = Arrays.toString(h);
            hobby = hobby.substring(1, hobby.length()-1);

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);

            Student student = new Student(sid,sname,gender,phone,hobby,info,date);
            StudentService service = new StudentServiceImpl();
            service.update(student);
            //跳转到servlet
            req.getRequestDispatcher("StudentListServlet").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
