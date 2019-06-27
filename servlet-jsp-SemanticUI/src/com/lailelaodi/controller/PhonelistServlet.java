package com.lailelaodi.controller;

import com.lailelaodi.pojo.PageBean;
import com.lailelaodi.service.PhoneService;
import com.lailelaodi.service.impl.PhoneServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName PhoneServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-27 下午9:58
 */
@WebServlet("/GetPhoneList")
public class PhonelistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            //获取页码
            int currentPage = Integer.parseInt(req.getParameter("currentPage"));
            //根据页码,获取数据
            PhoneService service = new PhoneServiceImpl();
            PageBean pageBean = service.findPhoneByPage(currentPage);

            //加载到作用域
            req.setAttribute("pageBean",pageBean);
            //跳转页面

            req.getRequestDispatcher("/cms/productcms.jsp").forward(req,resp);
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
