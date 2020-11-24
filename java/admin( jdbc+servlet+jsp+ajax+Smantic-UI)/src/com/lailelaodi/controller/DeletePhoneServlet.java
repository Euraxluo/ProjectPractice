package com.lailelaodi.controller;

import com.lailelaodi.pojo.ReturnMessage;
import com.lailelaodi.service.PhoneService;
import com.lailelaodi.service.impl.PhoneServiceImpl;
import com.lailelaodi.util.TextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName DeletePhoneServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-28 上午9:26
 */
@WebServlet("/Deletephone")
public class DeletePhoneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PhoneService service = new PhoneServiceImpl();
        TextUtils tojson = new TextUtils();
        try{
            String ids = req.getParameter("ids");
            String[] arr = ids.split(",");
            for(String x:arr) {
                int id = Integer.parseInt(x);
                service.delete(id);
            }
            tojson.getJson(req,resp, ReturnMessage.createByErrorMessage("删除成功"));
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
