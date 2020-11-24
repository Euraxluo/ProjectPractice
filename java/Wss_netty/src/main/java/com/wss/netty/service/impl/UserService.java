package com.wss.netty.service.impl;


import com.wss.netty.dao.ChatMsgMapper;
import com.wss.netty.dao.UsersMapper;
import com.wss.netty.enums.MsgSignFlagEnum;
import com.wss.netty.pojo.ChatMsg;
import com.wss.netty.pojo.SidBean;
import com.wss.netty.pojo.Users;
import com.wss.netty.pojo.dto.PersonInfoDTO;
import com.wss.netty.pojo.dto.UsersDTO;
import com.wss.netty.pojo.vo.UsersVO;
import com.wss.netty.service.IUserService;
import com.wss.netty.utils.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;

/**
 * Wss_netty
 * com.wss.netty.service.impl
 * UserService
 * 2019/10/15 13:59
 * author:Euraxluo
 * TODO
 */
@Service
public class UserService implements IUserService {
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private SidBean sid;

    @Autowired
    private QRCodeUtils qrCodeUtils;


    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 根据用户名判断用户是否存在,存在返回true
     *
     * @param username
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)//事务
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users users = new Users();
        users.setUsername(username);
        Users res = usersMapper.selectOne(users);
        return res != null ? true : false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String pwd) {
        Example usersExample = new Example(Users.class);
        Example.Criteria criteria = usersExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", pwd);
        Users res = usersMapper.selectOneByExample(usersExample);

        return res;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users saveUser(UsersDTO usersDto) {
        String userId = sid.get16LetterAndNum();
        Users users2save = new Users();
        users2save.setId(userId);
        users2save.setUsername(usersDto.getUsername());
        users2save.setPassword(MD5Utils.MD5EncodeUtf8(usersDto.getPassword()));
        users2save.setFaceImage(PropertiesUtil.getProperty("FaceImg", ""));
        users2save.setFaceImageBig(PropertiesUtil.getProperty("bigFaceImg", ""));

        users2save.setNickname(usersDto.getUsername());
        String qrCodePath = System.getProperty("java.io.tmpdir") + userId + "qrcode.png";
        qrCodeUtils.createQRCode(qrCodePath, "qrcode:" + usersDto.getUsername());
        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        users2save.setQrcode(qrCodeUrl);
        usersMapper.insert(users2save);

        return users2save;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UsersVO updateUserInfo(PersonInfoDTO infoDTO) {
        System.out.println(infoDTO.toString());
        Users users2update = new Users();
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(infoDTO, users2update);//dto->po
        usersMapper.updateByPrimaryKeySelective(users2update);//更新
        BeanUtils.copyProperties(queryUserById(infoDTO.getId()), usersVO);//po->vo
        return usersVO;

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserById(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UsersVO queryUserInfoByUserName(String username) {
        UsersVO usersVO = new UsersVO();
        Example userExam = new Example(Users.class);
        Example.Criteria userExamCriteria=  userExam.createCriteria();
        userExamCriteria.andEqualTo("username",username);
        BeanUtils.copyProperties(usersMapper.selectOneByExample(userExam), usersVO);//po->vo
        return usersVO;
    }

}
