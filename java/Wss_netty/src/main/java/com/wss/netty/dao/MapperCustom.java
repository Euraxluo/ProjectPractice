package com.wss.netty.dao;

import com.wss.netty.pojo.Users;
import com.wss.netty.pojo.vo.FriendsReqsVO;
import com.wss.netty.pojo.vo.MyFriendsVO;
import com.wss.netty.utils.MyMapper;

import java.util.List;

public interface MapperCustom extends MyMapper<Users> {
    public List<FriendsReqsVO> queryFriendReqsList(String acceptUserId);

    public List<MyFriendsVO> queryMyFriends(String userId);

    public void batchUpdateMsgSigned(List<String> msgIdList);
}