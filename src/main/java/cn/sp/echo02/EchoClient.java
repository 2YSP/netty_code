package cn.sp.echo02;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by 2YSP on 2019/4/30.
 */
public class EchoClient {

    private final String host;

    private final int port;

    public EchoClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args)throws Exception {
        new EchoClient("127.0.0.1",8000).start();
    }

    private void start()throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_", CharsetUtil.UTF_8);
                            socketChannel.pipeline()
                                    .addLast(new DelimiterBasedFrameDecoder(1024,delimiter))
                                    .addLast(new StringDecoder())
                                    .addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        }finally {
            // 关闭线程池并且释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}
