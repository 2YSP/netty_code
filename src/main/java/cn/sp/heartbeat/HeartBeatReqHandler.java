package cn.sp.heartbeat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by 2YSP on 2019/5/23.
 */
@ChannelHandler.Sharable
public class HeartBeatReqHandler extends SimpleChannelInboundHandler<String> {

    private volatile ScheduledFuture<?> heartBeat;

    private static final String hello = "start notify with server$_";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer(hello.getBytes()));
        System.out.println("================");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if ("ok".equalsIgnoreCase(msg)){
            //服务端返回ok开始心跳
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx),0,5000, TimeUnit.MILLISECONDS);
        }else {
            System.out.println("Client receive server heart beat message : --->"+msg);
        }
    }

    private class HeartBeatTask implements Runnable{

        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx){
            this.ctx = ctx;
        }


        @Override
        public void run() {
            String heartBeat = "I am ok";
            System.out.println("Client send heart beat message to server: ----->"+heartBeat);
            ctx.writeAndFlush(Unpooled.copiedBuffer((heartBeat+"$_").getBytes()));
        }
    }
}
