package com.lailelaodi.controller;

import com.lailelaodi.service.StudentService;
import com.lailelaodi.service.impl.StudentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName DeleteStudentServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-20 下午2:52
 */
public class DeleteStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            int sid = Integer.parseInt(req.getParameter("sid"));
            StudentService service = new StudentServiceImpl();
            service.delect(sid);
            req.getRequestDispatcher("StudentListServlet").forward(req,resp);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
