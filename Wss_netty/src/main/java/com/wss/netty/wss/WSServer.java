package com.wss.netty.wss;

import com.wss.netty.utils.PropertiesUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty_stu
 * websocket
 * WSServer
 * 2019/10/6 16:57
 * author:Euraxluo
 * TODO
 */
public class WSServer {
    //双重检查
    private volatile static WSServer instance;//可见性
    public static WSServer getInstance(){
        if (instance == null)
            synchronized (WSServer.class){
            if (instance ==null)
                instance = new WSServer();
            }
        return instance;
    }

    private EventLoopGroup masterGroup;
    private EventLoopGroup slaveGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    private WSServer() {
        masterGroup = new NioEventLoopGroup();
        slaveGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(masterGroup,slaveGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInit());
    }
    public void start() throws Exception{
        this.future = server.bind(Integer.parseInt(PropertiesUtil.getProperty("wss.port", "8000")));
        System.err.println("netty server started");
    }
}
