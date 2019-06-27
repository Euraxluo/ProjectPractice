package com.lailelaodi.controller;

import com.lailelaodi.pojo.Mobielcode;
import com.lailelaodi.pojo.ReturnMessage;
import com.lailelaodi.service.UserService;
import com.lailelaodi.service.impl.UserServiceImpl;
import com.lailelaodi.util.Sendsms;
import com.lailelaodi.util.TextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName GetmobilecodeServlet
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-26 上午9:42
 */
@WebServlet("/getmobilecode")
public class GetmobilecodeServlet extends HttpServlet {
    UserService service = new UserServiceImpl();
    TextUtils tojson = new TextUtils();
    Sendsms msgcode = new Sendsms();
    Mobielcode mobielcode = new Mobielcode();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String phone = req.getParameter("phone");
            boolean flag = TextUtils.isMobileNO(phone);
            if(flag){
                int retcode = msgcode.postcode(phone);
//                int retcode=111111;
                if(retcode == 0){
                    tojson.getJson(req,resp, ReturnMessage.createByErrorMessage("发送失败"));
                }else {
                    mobielcode.setCreatetime(TextUtils.now());
                    mobielcode.setPhone(phone);
                    mobielcode.setCode(retcode+"");

                    ReturnMessage retmsg = service.regiscode(mobielcode);
                    int code = retmsg.getCode();
                    if(code == 1){
                        tojson.getJson(req,resp, ReturnMessage.createBySuccessMessage("发送成功"));
                    }else {
                        tojson.getJson(req,resp,retmsg);
                    }
                }
            }else {
                tojson.getJson(req,resp, ReturnMessage.createByErrorMessage("手机号错误"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
