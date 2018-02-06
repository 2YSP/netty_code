package cn.sp.webSocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: 2YSP
 * @Description: 初始化ChannelPipeLine
 * @Date: Created in 2018/2/6
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private  final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group){
        this.group = group;
    }


    @Override
    protected void initChannel(Channel channel) throws Exception {
        //将所有需要的ChannelHandler添加到ChannelPipeLine中
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}
