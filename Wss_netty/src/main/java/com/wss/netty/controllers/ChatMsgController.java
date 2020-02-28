package com.wss.netty.controllers;

import com.wss.netty.pojo.ChatMsg;
import com.wss.netty.pojo.dto.PersonInfoDTO;
import com.wss.netty.service.impl.ChatMsgService;
import com.wss.netty.utils.JSON2Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Wss_netty
 * com.wss.netty.controllers
 * ChatMsgController
 * 2019/11/1 15:47
 * author:Euraxluo
 * TODO
 */
@RestController
@RequestMapping("c")
@Api(value = "聊天相关接口", tags = { "聊天" })
public class ChatMsgController {
    private static Logger log = Logger.getLogger(FriendsController.class);
    @Autowired
    private ChatMsgService chatMsgService;

    @PostMapping("/getUnReadMsgList")
    @ApiOperation(value = "获取未接受的消息列表")
    public JSON2Result getUnReadMsgList(@RequestBody @Valid PersonInfoDTO req){
        log.info(req.toString());
        List<ChatMsg> chatMsgList = chatMsgService.getUnReadMsgList(req.getId());
        return JSON2Result.ok(chatMsgList);
    }
}
