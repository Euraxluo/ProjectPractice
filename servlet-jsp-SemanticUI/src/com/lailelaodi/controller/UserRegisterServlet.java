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
 * @ClassName UserRegisterServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-25 下午8:57
 */
@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {
    UserService service = new UserServiceImpl();
    TextUtils tojson = new TextUtils();
    User user = new User();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //接受
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            String mobeilcode = req.getParameter("mobeilcode");
            //验证
            ReturnMessage retcheckcode = service.checkValid(mobeilcode,phone);

            if(retcheckcode.getCode()==1){

                //gouzao
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                user.setPhone(phone);

                //查询数据
                ReturnMessage retmsg = service.register(user);
                int code = retmsg.getCode();
                if(code == 1){
                    HttpSession session = req.getSession();
                    session.setMaxInactiveInterval(30*60);
                    session.setAttribute("id",username);
                }
                //显示数据
                tojson.getJson(req,resp,retmsg);

            }else {
                tojson.getJson(req,resp,retcheckcode);
            }
        } catch (SQLException e) {
            TextUtils tojson = new TextUtils();
            tojson.getJson(req,resp,ReturnMessage.createByErrorMessage("参数错误"));
        }
    }
}
