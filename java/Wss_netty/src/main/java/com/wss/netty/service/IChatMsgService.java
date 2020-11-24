package com.wss.netty.service;

import com.wss.netty.wss.ChatMsg;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Wss_netty
 * com.wss.netty.service
 * IChatMsgService
 * 2019/10/31 12:46
 * author:Euraxluo
 * TODO
 */
public interface IChatMsgService {

    public String saveMsg(ChatMsg chatMsg);

    public void updateMsgSigned(List<String> msgIdList);

    /**
     * 获取未读消息列表
     * @param acceptUserId
     * @return
     */
    public List<com.wss.netty.pojo.ChatMsg> getUnReadMsgList(String acceptUserId);
}
