package cn.sp.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by 2YSP on 2019/4/30.
 */
public class TimeClient {

    private final String host;
    private final int port;
    
    public TimeClient(String host,int port){
        this.port = port;
        this.host = host;
    }
    
    public static void main(String[] args)throws Exception {
        new TimeClient("127.0.0.1",8080).start();
    }

    private void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChildHandler());
            //发起异步连接操作
            ChannelFuture f = b.connect().sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    private class ChildHandler extends ChannelInitializer<SocketChannel>{
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeClientHandler());
        }
    }
}
