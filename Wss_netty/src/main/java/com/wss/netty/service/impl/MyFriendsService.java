package com.wss.netty.service.impl;

import com.wss.netty.dao.FriendsReqsMapper;
import com.wss.netty.dao.MyFriendsMapper;
import com.wss.netty.dao.MapperCustom;
import com.wss.netty.enums.MsgActionEnum;
import com.wss.netty.enums.MsgAddFriendsReqsEnum;
import com.wss.netty.enums.SearchFriendsStatusEnum;
import com.wss.netty.pojo.ChatMsg;
import com.wss.netty.pojo.FriendsReqs;
import com.wss.netty.pojo.MyFriends;
import com.wss.netty.pojo.SidBean;
import com.wss.netty.pojo.dto.MyFriendsDTO;
import com.wss.netty.pojo.vo.FriendsReqsVO;
import com.wss.netty.pojo.vo.MyFriendsVO;
import com.wss.netty.pojo.vo.UsersVO;
import com.wss.netty.service.IMyFriendsService;
import com.wss.netty.utils.JsonUtils;
import com.wss.netty.wss.DataContent;
import com.wss.netty.wss.UserChannelRel;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Wss_netty
 * com.wss.netty.service.impl
 * MyFriendsService
 * 2019/10/24 10:01
 * author:Euraxluo
 * TODO
 */
@Service
public class MyFriendsService implements IMyFriendsService {
    private static Logger log = Logger.getLogger(MyFriendsService.class);
    @Autowired
    private UserService userService;

    @Autowired
    private MapperCustom mapperCustom;

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Autowired
    private FriendsReqsMapper friendsReqsMapper;

    @Autowired
    private SidBean sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer SearchFriends(MyFriendsDTO myFriendsDTO) {
        //1. 判断用户是否存在
        if (!userService.queryUsernameIsExist(myFriendsDTO.getMyFrendUserName())){
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }
        //2. 判断搜索账号是你自己
        UsersVO friends =  userService.queryUserInfoByUserName(myFriendsDTO.getMyFrendUserName());
        if( friends.getId().equals(myFriendsDTO.getMyUserId())){
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        //3. 搜索的朋友已经是你的好友
        if (IsMyFriends(myFriendsDTO.getMyUserId(),friends.getId())){
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }

        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer sendFriendsRequest(MyFriendsDTO myFriendsDTO) {
        UsersVO friend = userService.queryUserInfoByUserName(myFriendsDTO.getMyFrendUserName());
        //如果发送过了,返回false
        if (IsSendAgain(myFriendsDTO.getMyUserId(),friend.getId())){
            log.info("add Friends Reqs IsSendAgain...");
            return MsgAddFriendsReqsEnum.REQS_AGAIN.status;
        }
        //否则调用添加接口
        FriendsReqs friendsReqs = new FriendsReqs();
        friendsReqs.setSendUserId(myFriendsDTO.getMyUserId());
        friendsReqs.setAcceptUserId(friend.getId());
        if (1==insertFriendsReqs(friendsReqs)){
            return MsgAddFriendsReqsEnum.SUCCESS.status;
        }
        return MsgAddFriendsReqsEnum.FAILD.status;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendsReqsVO> queryFriendReqsList(String acceptUserId) {
        return mapperCustom.queryFriendReqsList(acceptUserId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteFriendReqs(String sendUserId, String acceptUserId) {
        Example fre = new Example(FriendsReqs.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId",sendUserId);
        frc.andEqualTo("acceptUserId",acceptUserId);
        friendsReqsMapper.deleteByExample(fre);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void passFriendReqs(String sendUserId, String acceptUserId) {
        saveFriends(sendUserId,acceptUserId);
        saveFriends(acceptUserId,sendUserId);
        deleteFriendReqs(sendUserId,acceptUserId);
        //使用websocket来进行推送
        Channel sendChannel =  UserChannelRel.get(sendUserId);
        if (sendChannel != null){
            /** 使用ws主动推送消息到请求发起者,更新他的通讯录列表为最新 */
            DataContent dataContent = new DataContent();
            dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
            sendChannel.writeAndFlush(
                    new TextWebSocketFrame(
                            JsonUtils.objectToJson(dataContent)));
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        List<MyFriendsVO> myFriendsVOList = mapperCustom.queryMyFriends(userId);
        return myFriendsVOList;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    protected void saveFriends(String myUserId, String myFrendUserId){
        MyFriends myFriends = new MyFriends();
        String recordId = sid.get16LetterAndNum();
        myFriends.setId(recordId);
        myFriends.setMyUserId(myUserId);
        myFriends.setMyFrendUserId(myFrendUserId);
        myFriendsMapper.insert(myFriends);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int insertFriendsReqs(FriendsReqs friendsReqs){
        String reqId = sid.get16LetterAndNum();
        friendsReqs.setId(reqId);
        friendsReqs.setReqDateTime(new Date());
        return friendsReqsMapper.insert(friendsReqs);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean IsMyFriends(String myUserId,String friendsId){
        Example myFriendsExample = new Example(MyFriends.class);
        Example.Criteria myFriendsCriteria = myFriendsExample.createCriteria();
        myFriendsCriteria.andEqualTo("myUserId",myUserId);
        myFriendsCriteria.andEqualTo("myFrendUserId",friendsId);
        MyFriends myFriends =  myFriendsMapper.selectOneByExample(myFriendsExample);
        return myFriends != null? true:false;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean IsSendAgain(String myUserId,String friendsId){
        Example friendsReqExample = new Example(FriendsReqs.class);
        Example.Criteria friendsReqCriteria = friendsReqExample.createCriteria();
        friendsReqCriteria.andEqualTo("sendUserId",myUserId);
        friendsReqCriteria.andEqualTo("acceptUserId",friendsId);
        FriendsReqs friendsReqs =  friendsReqsMapper.selectOneByExample(friendsReqExample);
        return friendsReqs != null? true:false;
    }
}
