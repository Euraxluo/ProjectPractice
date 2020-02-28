package com.wss.netty.wss;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Wss_netty
 * com.wss.netty.wss
 * HeartBeatHandler
 * 2020/2/28 12:22
 * author:Euraxluo
 * 心跳检测相关handler
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
        /** 判断evt是否时IdleStateEvent(用于触发用户事件,包含读空闲/写空闲/读写空闲)*/
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE){
//                log.info("IdleState.READER_IDLE");
            }
            else if (event.state() == IdleState.WRITER_IDLE){
//                log.info("IdleState.WRITER_IDLE");
            }
            else if (event.state() == IdleState.ALL_IDLE){
                Channel channel = ctx.channel();
                channel.close();
                log.info("IdleState.ALL_IDLE,Channel online : {}",ChatHandler.users.size());
            }
        }
    }
}
