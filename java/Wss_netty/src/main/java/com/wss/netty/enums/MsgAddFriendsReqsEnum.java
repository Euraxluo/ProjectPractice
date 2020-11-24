package com.wss.netty.enums;

/**
 * Wss_netty
 * com.wss.netty.enums
 * MsgAddFriendsReqsEnum
 * 2019/10/24 17:19
 * author:Euraxluo
 * TODO
 */
public enum MsgAddFriendsReqsEnum {
    SUCCESS(0, "请求发送成功"),
    REQS_AGAIN(1, "已经发送过请求"),
    FAILD(2, "请求发送失败");

    public final Integer status;
    public final String msg;

    MsgAddFriendsReqsEnum(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public static String getMsgByKey(Integer status) {
        for (MsgAddFriendsReqsEnum type : MsgAddFriendsReqsEnum.values()) {
            if (type.getStatus() == status) {
                return type.msg;
            }
        }
        return null;
    }
}
