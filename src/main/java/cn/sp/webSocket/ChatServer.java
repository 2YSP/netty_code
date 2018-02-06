package cn.sp.webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * @Author: 2YSP
 * @Description:
 * @Date: Created in 2018/2/6
 */
public class ChatServer {

    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    private EventLoopGroup group = new NioEventLoopGroup();

    private Channel channel;

    public ChannelFuture start(InetSocketAddress address){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup));

        ChannelFuture future = bootstrap.bind(address);
        channel = future.channel();
        return future;
    }

    private ChannelInitializer<Channel> createInitializer(ChannelGroup channelGroup) {
        return new ChatServerInitializer(channelGroup);
    }

    public void destory(){
        if (channel != null){
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
        if (args.length != 1){
            System.err.println("Please give port as argument");
            System.exit(1);
        }

        int port = Integer.valueOf(args[0]);
        final ChatServer endpoint = new ChatServer();
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                endpoint.destory();
            }
        });

        future.channel().closeFuture().syncUninterruptibly();
    }
}
