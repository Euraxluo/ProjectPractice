package com.lailelaodi.controller;

import com.lailelaodi.pojo.ReturnMessage;
import com.lailelaodi.pojo.User;
import com.lailelaodi.service.UserService;
import com.lailelaodi.service.impl.UserServiceImpl;
import com.lailelaodi.util.TextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName UserLoginServlety
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-24 下午5:01
 */
@WebServlet("/checkuser")
public class UserLoginServlet extends HttpServlet {
    UserService service = new UserServiceImpl();
    TextUtils tojson = new TextUtils();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //接受
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            //查询数据

            ReturnMessage retmsg=service.login(username,password);
            int code = retmsg.getCode();
            if(code == 1){
                HttpSession session = req.getSession();
                session.setMaxInactiveInterval(30*60);
                session.setAttribute("id",username);
            }
            //显示数据
            tojson.getJson(req,resp,retmsg);
        } catch (SQLException e) {
            TextUtils tojson = new TextUtils();
            tojson.getJson(req,resp,ReturnMessage.createByErrorMessage("参数错误"));
        }
    }
}
