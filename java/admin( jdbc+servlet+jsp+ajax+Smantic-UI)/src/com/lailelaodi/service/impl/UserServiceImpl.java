package com.lailelaodi.service.impl;

import com.lailelaodi.dao.MobielcodeDao;
import com.lailelaodi.dao.UserDao;
import com.lailelaodi.dao.impl.MobielcodeDaoImpl;
import com.lailelaodi.dao.impl.UserDaoImpl;
import com.lailelaodi.pojo.Mobielcode;
import com.lailelaodi.pojo.ReturnMessage;
import com.lailelaodi.pojo.User;
import com.lailelaodi.service.UserService;
import com.lailelaodi.util.MD5Util;
import com.lailelaodi.util.TextUtils;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.sql.SQLException;

import static com.sun.xml.internal.ws.api.message.Packet.State.ServerResponse;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-24 下午11:24
 */
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    MobielcodeDao mobielcodeDao = new MobielcodeDaoImpl();
    @Override
    public ReturnMessage login(String username, String password) throws SQLException {
        TextUtils tojson = new TextUtils();
        int resultCount = userDao.checkUsername(username);
        if(resultCount == 0){
            return ReturnMessage.createByErrorMessage("用户名或密码错误");

        }
        //密码需要和MD5加密后的进行比较
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userDao.selectLogin(username,md5Password);
        if(user == null){//用户名存在,但密码错误,返回的值为空
            return ReturnMessage.createByErrorMessage("用户名或密码错误");
        }
        //处理返回值密码
        user.setUsername(username);
        user.setPassword("");
        return ReturnMessage.createBySuccess(username);
    }

    @Override
    public ReturnMessage register(User user) throws SQLException {
        //为了把代码复用(checkValid会同时在controller中使用,但是其中也可以用来判断),我们把checkValid在这里用来作为校验
        ReturnMessage validResponse = this.checkValid(user.getUsername(),TextUtils.USERNAME);
        if(validResponse.getCode()==0){
            return validResponse;
        }

        validResponse = this.checkValid(user.getEmail(),TextUtils.EMAIL);
        if(validResponse.getCode()==0){
            return validResponse;
        }

        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        //把密码插入到数据库
        int resultCount = userDao.insert(user);
        if(resultCount == 0){
            return ReturnMessage.createByErrorMessage("注册失败");
        }
        return ReturnMessage.createBySuccessMessage("注册成功");
    }

    @Override
    public ReturnMessage regiscode(Mobielcode mobielcode) throws SQLException {
        ReturnMessage validResponse = this.checkValid(mobielcode.getPhone(),TextUtils.PHONE);
        if(validResponse.getCode()==0){
            return validResponse;
        }
        //把密码插入到数据库
        int resultCount = mobielcodeDao.insert(mobielcode);
        if(resultCount == 0){
            return ReturnMessage.createByErrorMessage("发送失败");
        }
        return ReturnMessage.createBySuccessMessage("发送成功");
    }

    @Override
    public ReturnMessage checkValid(String str, String type) throws SQLException {
        if(TextUtils.isNotBlank(type)){
            //开始校验
            if(TextUtils.USERNAME.equals(type)){
                int resultCount = userDao.checkUsername(str);
                if(resultCount > 0){
                    return ReturnMessage.createByErrorMessage("用户名已存在!");
                }
            }

            if(TextUtils.EMAIL.equals(type)){
                int resultCount = userDao.checkEmail(str);
                if(resultCount > 0){
                    return ReturnMessage.createByErrorMessage("邮箱已存在!");
                }
            }

            if(TextUtils.PHONE.equals(type)){
                int resultCount = userDao.checkPhone(str);
                if(resultCount > 0){
                    return ReturnMessage.createByErrorMessage("手机号已注册!");
                }
            }
            if(type.length()==11){
                int resultCount = mobielcodeDao.checkCode(type,str);
                if(resultCount == 0){
                    return ReturnMessage.createByErrorMessage("验证码已失效!");
                }
            }
        }else {
            return ReturnMessage.createByErrorMessage("参数错误!");
        }
        return ReturnMessage.createBySuccessMessage("校验成功!");
    }

}
