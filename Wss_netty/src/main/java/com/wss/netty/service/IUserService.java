package com.wss.netty.service;

import com.wss.netty.pojo.Users;
import com.wss.netty.pojo.dto.PersonInfoDTO;
import com.wss.netty.pojo.dto.UsersDTO;
import com.wss.netty.pojo.vo.UsersVO;
import com.wss.netty.wss.ChatMsg;

import java.util.List;

/**
 * Wss_netty
 * com.wss.netty.service
 * IUserService
 * 2019/10/15 13:48
 * author:Euraxluo
 * TODO
 */
public interface IUserService {
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 查询用户是否存在
     * @param username
     * @param pwd
     * @return
     */
    public Users queryUserForLogin(String username,String pwd);

    /**
     * 存储用户
     * @param usersDto
     * @return
     */
    public Users saveUser(UsersDTO usersDto);

    /**
     * 修改用户
     * @param infoDTO
     */
    public UsersVO updateUserInfo(PersonInfoDTO infoDTO);

    /**
     * 根据ID查询用户
     * @param userId
     * @return
     */
    public  Users queryUserById(String userId);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public UsersVO queryUserInfoByUserName(String username);
}
