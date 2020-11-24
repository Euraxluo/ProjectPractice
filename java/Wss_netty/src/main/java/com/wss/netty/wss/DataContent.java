package com.wss.netty.wss;

import com.wss.netty.wss.ChatMsg;
import lombok.Data;

import java.io.Serializable;

/**
 * Wss_netty
 * com.wss.netty.wss
 * DataContent
 * 2019/10/30 18:15
 * author:Euraxluo
 * TODO
 */
@Data
public class DataContent implements Serializable {
    private static final long serialVersionUID = 5299670538365195255L;
    private Integer action;//动作类型
    private ChatMsg chatMsg;//聊天内容
    private String extend;//扩展字段

}
