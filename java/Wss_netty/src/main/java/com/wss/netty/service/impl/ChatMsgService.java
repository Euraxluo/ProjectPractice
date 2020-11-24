package com.wss.netty.service.impl;

import com.wss.netty.dao.ChatMsgMapper;
import com.wss.netty.dao.MapperCustom;
import com.wss.netty.enums.MsgSignFlagEnum;
import com.wss.netty.pojo.ChatMsg;
import com.wss.netty.pojo.SidBean;
import com.wss.netty.service.IChatMsgService;
import org.apache.commons.lang3.StringUtils;
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
 * IChatMsgService
 * 2019/10/31 12:46
 * author:Euraxluo
 * TODO
 */
@Service
public class ChatMsgService implements IChatMsgService {

    @Autowired
    private SidBean sid;

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Autowired
    private MapperCustom mapperCustom;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveMsg(com.wss.netty.wss.ChatMsg chatMsg) {
        ChatMsg chatMsgpojo = new ChatMsg();
        String msgId = sid.get16LetterAndNum();
        chatMsgpojo.setId(msgId);
        chatMsgpojo.setAcceptUserId(chatMsg.getReceiverId());
        chatMsgpojo.setSendUserId(chatMsg.getSenderId());
        chatMsgpojo.setCreateTime(new Date());
        chatMsgpojo.setSignFlag(MsgSignFlagEnum.unsign.type);//未签收
        chatMsgpojo.setMsg(chatMsg.getMsg());
        chatMsgMapper.insert(chatMsgpojo);
        return msgId;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        mapperCustom.batchUpdateMsgSigned(msgIdList);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ChatMsg> getUnReadMsgList(String acceptUserId) {
        Example chatMsgExample = new Example(ChatMsg.class);
        Example.Criteria chatMsgCriteria = chatMsgExample.createCriteria();
        chatMsgCriteria.andEqualTo("signFlag",0);
        chatMsgCriteria.andEqualTo("acceptUserId",acceptUserId);
        List<ChatMsg> chatMsgList =  chatMsgMapper.selectByExample(chatMsgExample);
        return chatMsgList;
    }

}
