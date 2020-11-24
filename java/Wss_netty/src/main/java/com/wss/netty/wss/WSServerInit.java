package com.wss.netty.wss;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * netty_stu
 * websocket
 * WSServerInit
 * 2019/10/6 17:01
 * author:Euraxluo
 * TODO
 */
public class WSServerInit extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline =  socketChannel.pipeline();
        /**
         * http协议支持
         */
        //wss 基于http协议,需要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        //大数据流的写支持
        pipeline.addLast(new ChunkedWriteHandler());
        //一个httpMessage相关handle的聚合器,在netty中的编程都会使用这个handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        /**
         * websocket协议相关
         */
        //本handler是和WSS相关,指定给客户端连接的路由:/ws,处理数据帧
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new ChatHandler());//自定义

        /**
         * 心跳相关
         * */
        //如果子1分钟时没有向服务端发送读写心跳包,那么关闭这个channel
        pipeline.addLast(new IdleStateHandler(2,4,60));
        pipeline.addLast(new HeartBeatHandler());


    }
}
