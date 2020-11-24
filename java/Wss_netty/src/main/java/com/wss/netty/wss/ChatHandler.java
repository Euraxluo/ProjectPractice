package com.wss.netty.wss;

import com.wss.netty.enums.MsgActionEnum;
import com.wss.netty.service.IChatMsgService;
import com.wss.netty.service.impl.ChatMsgService;
import com.wss.netty.service.impl.UserService;
import com.wss.netty.utils.JsonUtils;
import com.wss.netty.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * netty_stu
 * websocket
 * ChatHandler
 * 2019/10/6 17:13
 * author:Euraxluo
 * 处理消息的handler,用于为WSS处理文本的对象,frame是消息的载体:数据帧
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //初始化ChannelGroup 用于管理所有客户端的channel,保存到一个group中
    protected static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 当客户端连接服务端后
     * 获取客户端的channel,放到ChannelGroup中进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        log.info("客户端被移除,channelId : {}",channelId);
        users.remove(ctx.channel()); //netty在触发handlerRemoved,会自动将channel从users中移除
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();//发生异常后关闭连接,然后移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg)
            throws Exception {
        /**获取客户端的消息*/
        String content = msg.text();
        Channel currentChannel = ctx.channel();//获取当前的channel
        /**判断消息类型*/
        /**
         * 	CONNECT(1, "第一次(或重连)初始化连接"),
         * 	CHAT(2, "聊天消息"),
         * 	SIGNED(3, "消息签收"),
         * 	KEEPALIVE(4, "客户端保持心跳"),
         * 	PULL_FRIEND(5, "拉取好友");
         * */
        DataContent dataContent =  JsonUtils.jsonToPojo(content,DataContent.class);
        Integer action = dataContent.getAction();
        if(action == MsgActionEnum.CONNECT.type){
            /**1. ws第一次打开或者重连时,将当前的senderId与当前的channel绑定*/
            //获取消息发送者
            String senderId = dataContent.getChatMsg().getSenderId();
            //绑定消息发送者与chaannel
            UserChannelRel.put(senderId,currentChannel);
        }else if (action == MsgActionEnum.CHAT.type){
            /**2.聊天类型的消息,把聊天记录保存到数据库,并且把消息的状态设置为未签收 */
            /**
             * 1) 保存消息,设置状态
             * wss给的数据不太完整,只有简单的一些内容
             * 保存到数据库时,会生成msgId,填充数据
             * 以及把签收状态填充为未签收
             */
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgText = chatMsg.getMsg();
            String receiverId = chatMsg.getReceiverId();
            String senderId = chatMsg.getSenderId();
            //保存到数据库中,默认为未签收
            //调用service,但是无法进行注入,需要手动提取spring上下文获取bean
            ChatMsgService chatMsgService = (ChatMsgService) SpringUtil.getBean("chatMsgService");
            String msgId =  chatMsgService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            /** 2) 将消息推送到接收方,获取接收方的channel*/
            Channel receiverChannel =  UserChannelRel.get(receiverId);

            if (receiverChannel == null){
                log.info("{} : offline,receiverChannel==null",receiverId);
                //todo 用户离线,需要第三方推送
            }else {
                //需要去ChannelGroup查找这个channel,以判断用户是否在线
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null){
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(
                            JsonUtils.objectToJson(chatMsg)
                    ));
                }else {
                    log.info("{} : offline,ChannelGroup find faild",receiverId);
                    //todo 用户离线,需要第三方推送
                }
            }
        }else if (action == MsgActionEnum.SIGNED.type){
            /**3.签收消息类型,针对具体的消息进行签收,修改数据库中对应消息的签收状态为已签收  */
            ChatMsgService chatMsgService = (ChatMsgService) SpringUtil.getBean("chatMsgService");
            //扩展字段在signed类型的消息中代表需要签收的消息Id,逗号分隔,
            // 我们需要将其转化为List[msgIds],循环对于这些id进行处理
            String msgIdStr = dataContent.getExtend();
            String msgIds[] = msgIdStr.split(",");
            List<String> msgIdList = new ArrayList<>();
            //填充msgIdList,排除点空值
            for (String mid: msgIds){
                if(StringUtils.isNoneBlank(mid)){
                    msgIdList.add(mid);
                }
            }

            log.info("msgIds List: {}",msgIdList.toString());
            //判断msgIdList中是否有值
            if( msgIdList != null && !msgIdList.isEmpty()){
                //批量签收
                chatMsgService.updateMsgSigned(msgIdList);
            }
        }else if (action == MsgActionEnum.KEEPALIVE.type){
            /**4.心跳类型消息 */
            log.info("收到来自channel为: {} 的心跳包",currentChannel);
            //心跳处理
        }


        System.out.println("接受的数据:"+content);

        //循环从group中writeAndFlush
//        for (Channel channel: users) {
//            channel.writeAndFlush(new TextWebSocketFrame(
//                    "[服务器接收到消息] 时间:"+LocalDateTime.now()+" 消息:"+content));
//        }
//        users.writeAndFlush( new TextWebSocketFrame(
//                "{\"time\":\""+LocalDateTime.now()+"\",\"content\":"+content+"}")
//        );
    }
}
