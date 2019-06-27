package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ClassName UserController
 * @Description 前台用户界面
 * @Author Euraxluo
 * @Date 18-12-7 下午2:55
 */
@Controller//设置为Controller
@RequestMapping("/user/")//设置命名空间
public class UserController {
    @Autowired
    private IUserService iUserService;
    /**
     * @description 用户登录
     * @param [username, password, session]
     * @return java.lang.Object
     * @author Euraxluo
     * @date 18-12-7
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<User> login(String username, String password, HttpSession session){
        //service->mybatis->dao
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * @description 登出接口
     * @param [session]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-8
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * @description 注册接口
     * @param [user]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-8
     */
    @RequestMapping(value = "register.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> register(User user){
        return iUserService.register(user);

    }

    /**
     * @description 校验接口
     * @param [str, type]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-8
     */
    //为了防止恶意调用接口,需要再加一个校验部分,type用来判断是用户名还是邮箱,对应相应的sql
    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    /**
     * @description 请求用户信息
     * @param [session]
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     * @author Euraxluo
     * @date 18-12-11
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取用户当前信息");
    }

    /**
     * @description 查询并返回我们的问题
     * @param [username]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-11
     */
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }
    /**
     * @description 使用本地缓存检查问题的答案是否正确
     * @param [username, question, answer]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-11
     */
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * @description 忘记密码时修改密码
     * @param [username, passwordNew, forgetToken]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-12
     */
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * @description 在登录状态时,修改密码
     * @param [session, passwordOld, passwordNew]
     * @return com.mmall.common.ServerResponse<java.lang.String>
     * @author Euraxluo
     * @date 18-12-12
     */
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * @description 更新个人信息
     * @param [session, user]
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     * @author Euraxluo
     * @date 18-12-12
     */
    //session验证是否登录,会把新的userinfo放到session中,user为了承载数据,来update,并且及时返回给前端
    @RequestMapping(value = "update_infomation.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<User> update_infomation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //我们更新时用户填写的数据没有userId,需要我们自己从session获取
        user.setId(currentUser.getId());
        //updata
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * @description 获取用户详细信息,service会把密码设为空
     * @param [session]
     * @return com.mmall.common.ServerResponse<com.mmall.pojo.User>
     * @author Euraxluo
     * @date 18-12-12
     */
    @RequestMapping(value = "get_infomation.do",method = RequestMethod.POST)//请求地址和类型
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse<User> get_infomation(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录");
        }
        return iUserService.getInfomation(currentUser.getId());

    }
}