package com.wss.netty.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wss_netty
 * com.wss.netty.pojo.vo
 * FriendsReqsVO
 * 2019/10/25 20:59
 * author:Euraxluo
 * TODO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendsReqsVO {
    private String sendUsername;
    private String sendUserId;
    private String sendFaceImage;
    private String sendNickname;
}