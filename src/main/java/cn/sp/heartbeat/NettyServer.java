package cn.sp.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by 2YSP on 2019/5/23.
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bind(9911);
    }

    private void bind(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());

                            ch.pipeline()
                                    .addLast(new DelimiterBasedFrameDecoder(1024,delimiter))
                                    .addLast(new StringDecoder())
                                    .addLast("readTimeOutHandler",new ReadTimeoutHandler(50))
                                    .addLast("HeartBeatHandler",new HeartBeatRespHandler());
                        }
                    });
            // 绑定端口，同步等待成功
             b.bind(port).sync();
            System.out.println("Netty Server start ok ....");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
