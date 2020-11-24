package com.wss.netty.controllers;

import com.wss.netty.pojo.Users;
import com.wss.netty.pojo.dto.PersonInfoDTO;
import com.wss.netty.pojo.dto.UsersDTO;
import com.wss.netty.pojo.vo.UsersVO;
import com.wss.netty.service.impl.UserService;
import com.wss.netty.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Wss_netty
 * com.wss.netty.demo.controllers
 * HelloController
 * 2019/10/10 18:08
 * author:Euraxluo
 * TODO
 */
@RestController
@RequestMapping("u")
@Api(value = "用户相关接口", tags = { "用户" })
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private FastDFSClient fastDFSClient;

    @PostMapping("/registOrLogin")
    @ApiOperation(value = "注册/登录")
    public JSON2Result registOrLogin(@RequestBody @Valid UsersDTO req){
        System.out.println(req.toString());
        //判断用户名是否存在
        boolean usernameIsExit = userService.queryUsernameIsExist(req.getUsername());
        //如果存在那么登录,不存在则注册
        Users users = new Users();
        if (usernameIsExit){
            users = userService.queryUserForLogin(req.getUsername()
                    , MD5Utils.MD5EncodeUtf8(req.getPassword()));
            if (users==null)
                return JSON2Result.errorMsg("用户名或密码不正确");
        }else {
            users = userService.saveUser(req);

        }

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users,usersVO);//拷贝一个vo

        return JSON2Result.ok(usersVO);
    }


    @PostMapping("/uploadFaceBase64")
    @ApiOperation(value = "上传头像")
    public JSON2Result uploadFaceBase64(@RequestParam("file") MultipartFile file,@RequestParam("id") String id) throws Exception {
        String bigImg_url = fastDFSClient.uploadFile(file);// . uploadBase64(faceFile);
        String thump ="_"
                +PropertiesUtil.getProperty("fdfs.thumbImage.width", "80")
                +"x"
                +PropertiesUtil.getProperty("fdfs.thumbImage.height", "80")
                +".";
        String urlArr[] = bigImg_url.split("\\.");
        String thumpImgUrl = urlArr[0]+thump+urlArr[1];
        log.info("上传成功: "+bigImg_url);
        PersonInfoDTO req = new PersonInfoDTO(id,bigImg_url,thumpImgUrl,null);
        return JSON2Result.ok(userService.updateUserInfo(req));
    }

    @PostMapping("/setNickname")
    @ApiOperation(value = "设置nickname")
    public JSON2Result setNickname(@RequestBody @Valid PersonInfoDTO req) {
        return JSON2Result.ok(userService.updateUserInfo(req));
    }


}

