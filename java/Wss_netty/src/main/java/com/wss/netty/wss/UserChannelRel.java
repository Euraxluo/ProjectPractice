package com.wss.netty.wss;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * Wss_netty
 * com.wss.netty.wss
 * UserChannelRel
 * 2019/10/31 10:59
 * author:Euraxluo
 * 提供静态方法实现用户id和channel的绑定
 */
public class UserChannelRel {
    private static HashMap<String, Channel> manager = new HashMap<>();
    public static void put(String senderId,Channel channel){
        manager.put(senderId,channel);
    }
    public static Channel get(String senderId){
        return manager.get(senderId);
    }
}
