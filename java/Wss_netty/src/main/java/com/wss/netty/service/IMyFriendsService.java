package com.wss.netty.service;

import com.wss.netty.pojo.ChatMsg;
import com.wss.netty.pojo.dto.MyFriendsDTO;
import com.wss.netty.pojo.vo.FriendsReqsVO;
import com.wss.netty.pojo.vo.MyFriendsVO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Wss_netty
 * com.wss.netty.service
 * IMyFriendsService
 * 2019/10/24 9:58
 * author:Euraxluo
 * TODO
 */
public interface IMyFriendsService {
    /**
     * 搜索好友
     * @param myFriendsDTO
     * @return
     */
    public Integer SearchFriends(MyFriendsDTO myFriendsDTO);

    /**
     * 发送好友请求
     * @param myFriendsDTO
     * @return
     */
    public Integer sendFriendsRequest(MyFriendsDTO myFriendsDTO);

    /**
     * 查询好友请求
     * @param acceptUserId
     * @return
     */
    public List<FriendsReqsVO> queryFriendReqsList(String acceptUserId);

    /**
     * 删除好友请求
     * @param sendUserId
     * @param acceptUserId
     */
    public void deleteFriendReqs(String sendUserId,String acceptUserId);

    /**
     * 通过好友请求
     * @param sendUserId
     * @param acceptUserId
     */
    public void passFriendReqs(String sendUserId,String acceptUserId);

    /**
     * 查询得到好友列表
     * @param userId
     * @return
     */
    public List<MyFriendsVO> queryMyFriends(String userId);

}
