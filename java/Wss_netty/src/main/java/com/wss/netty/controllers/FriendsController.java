package com.wss.netty.controllers;

import com.wss.netty.enums.MsgAddFriendsReqsEnum;
import com.wss.netty.enums.OperatorFriendRequestTypeEnum;
import com.wss.netty.enums.SearchFriendsStatusEnum;
import com.wss.netty.pojo.ChatMsg;
import com.wss.netty.pojo.dto.MyFriendsDTO;
import com.wss.netty.pojo.dto.OperFriendReqsDTO;
import com.wss.netty.pojo.dto.PersonInfoDTO;
import com.wss.netty.pojo.vo.MyFriendsVO;
import com.wss.netty.service.impl.ChatMsgService;
import com.wss.netty.service.impl.MyFriendsService;
import com.wss.netty.service.impl.UserService;
import com.wss.netty.utils.JSON2Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Wss_netty
 * com.wss.netty.controllers
 * friendsController
 * 2019/10/24 7:40
 * author:Euraxluo
 * TODO
 */
@RestController
@RequestMapping("f")
@Api(value = "朋友相关接口", tags = { "朋友" })
public class FriendsController {
    private static Logger log = Logger.getLogger(FriendsController.class);
    @Autowired
    private MyFriendsService myFriendsService;
    @Autowired
    private UserService userService;
    @PostMapping("/search")
    @ApiOperation(value = "搜索好友")
    public JSON2Result searchUser(@RequestBody @Valid MyFriendsDTO req) {
        Integer status = myFriendsService.SearchFriends(req);
        if (status == SearchFriendsStatusEnum.SUCCESS.status){
            return JSON2Result.ok(userService.queryUserInfoByUserName(req.getMyFrendUserName()));
        }
        String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
        return JSON2Result.errorMsg(errorMsg);
    }

    @PostMapping("/addFriendRequest")
    @ApiOperation(value = "添加好友请求")
    public JSON2Result addFriends(@RequestBody @Valid MyFriendsDTO req) {
        Integer status = myFriendsService.SearchFriends(req);
        if (status != SearchFriendsStatusEnum.SUCCESS.status){
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(status);
            return JSON2Result.errorMsg(errorMsg);
        }

        Integer sendReqStatus = myFriendsService.sendFriendsRequest(req);
        String sendReqMsg = MsgAddFriendsReqsEnum.getMsgByKey(sendReqStatus);
        if (sendReqStatus == MsgAddFriendsReqsEnum.SUCCESS.status){
            return JSON2Result.ok(sendReqMsg);
        }
        return JSON2Result.errorMsg(sendReqMsg);
    }

    @PostMapping("/queryFriendRequests")
    @ApiOperation(value = "查看好友请求列表")
    public JSON2Result queryFriendsReqsList(@RequestBody @Valid PersonInfoDTO myinfo){
        log.info(myinfo.toString()+"查询好友情求");
        return JSON2Result.ok(myFriendsService.queryFriendReqsList(myinfo.getId()));
    }

    @PostMapping("/operFriendRequest")
    @ApiOperation(value = "好友请求操作接口")
    public JSON2Result operFriendReqs(@RequestBody @Valid OperFriendReqsDTO req){
        log.info(req.toString());

        //1. 如果operType没有对应的枚举值,则直接抛出空的错误信息
        if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(req.getOperType()))){
            return JSON2Result.errorMsg("");
        }
        if (req.getOperType() == OperatorFriendRequestTypeEnum.IGNORE.type){
            //2.如果忽略好友请求,则直接删除好友请求表的信息
            myFriendsService.deleteFriendReqs(req.getSendUserId(),req.getAcceptUserId());
        }else if (req.getOperType()==OperatorFriendRequestTypeEnum.PASS.type){
            //3.如果是通过,互相增加好友记录到数据库中对应的表中
            //然后删除好友请求记录表中的信息
            myFriendsService.passFriendReqs(req.getSendUserId(),req.getAcceptUserId());
        }
        List<MyFriendsVO> myFriendsVOList = myFriendsService.queryMyFriends((req.getAcceptUserId()));
        return JSON2Result.ok(myFriendsVOList);
    }

    @PostMapping("/myFriends")
    @ApiOperation(value = "查询好友列表")
    public JSON2Result myFriends(@RequestBody @Valid PersonInfoDTO req){
        log.info(req.toString());
        List<MyFriendsVO> myFriendsVOList = myFriendsService.queryMyFriends((req.getId()));
        return JSON2Result.ok(myFriendsVOList);
    }
}
