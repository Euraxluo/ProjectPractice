package com.lailelaodi.controller;

import com.lailelaodi.pojo.PageBean;
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
 * @ClassName SearchPhoneServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-28 下午12:42
 */
@WebServlet("/SearchPhone")
public class SearchPhoneServlet  extends HttpServlet {
    TextUtils tojson = new TextUtils();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            int id = -1,nums = -1;
            String checkid = req.getParameter("id");
            String checknums = req.getParameter("nums");
            if(!TextUtils.isEmpty(checkid)){
                id = Integer.parseInt(checkid);
            }
            if(!TextUtils.isEmpty(checknums)){
                nums = Integer.parseInt(checknums);
            }
            //取值
            String phoneName = req.getParameter("phoneName");
            String theme = req.getParameter("theme");
            //找service查询
            PhoneService service =new PhoneServiceImpl();
            PageBean pageBean = service.searchPhone(id,phoneName,theme,nums);
            //存储在作用域中
            req.setAttribute("pageBean", pageBean);
//            System.out.println(pageBean);
            //跳转,直接跳转
            tojson.getJson(req,resp,ReturnMessage.createBySuccessMessage("OK"));
        } catch (SQLException e) {
            tojson.getJson(req,resp, ReturnMessage.createByErrorMessage("参数错误"));
        }
    }
}
