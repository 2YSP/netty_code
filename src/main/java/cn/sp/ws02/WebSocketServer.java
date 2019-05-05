package cn.sp.ws02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by 2YSP on 2019/5/5.
 */
public class WebSocketServer {

    public static void main(String[] args)throws Exception {
        new WebSocketServer().run(8080);
    }

    public void run(int port)throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-codec",new HttpServerCodec())
                            .addLast("aggregator",new HttpObjectAggregator(65536))
                            .addLast("http-chunked",new ChunkedWriteHandler())
                            .addLast(new WebSocketServerHandler());
                        }
                    });
            Channel ch = b.bind(port).sync().channel();
            System.out.println("Web socket server stated at port "+port+".");
            System.out.println("Open your browser and navigate to http://localhost:"+port+"/");
            ch.closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
