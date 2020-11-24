package com.lailelaodi.controller;

import com.alibaba.fastjson.JSON;
import com.lailelaodi.pojo.ReturnMessage;
import com.lailelaodi.util.TextUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName CheckCode
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-24 下午8:49
 */
@WebServlet("/checkCode")
public class CheckCode extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        // 验证验证码
        String sessionCode = req.getSession().getAttribute("code").toString();
        if (code != null && !"".equals(code) && sessionCode != null && !"".equals(sessionCode)) {
            if (code.equalsIgnoreCase(sessionCode)) {
                req.getRequestDispatcher("/checkuser").forward(req,resp);
            } else {
                outputToJsp(req,resp);
            }
        } else {
            outputToJsp(req,resp);
        }
    }

    private void outputToJsp(HttpServletRequest req, HttpServletResponse resp) {
        TextUtils tojson = new TextUtils();
        tojson.getJson(req,resp,ReturnMessage.createByErrorMessage("验证码错误"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

}
