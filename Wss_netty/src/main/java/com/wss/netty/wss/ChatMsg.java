package com.wss.netty.wss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Wss_netty
 * com.wss.netty.wss
 * ChatMsg
 * 2019/10/30 18:19
 * author:Euraxluo
 * TODO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 7883366351662672299L;
    private String senderId;
    private String receiverId;
    private String msg;
    private String msgId;
}
