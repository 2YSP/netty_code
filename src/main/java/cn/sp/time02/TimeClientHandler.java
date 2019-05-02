package cn.sp.time02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by 2YSP on 2019/4/30.
 */
public class TimeClientHandler extends SimpleChannelInboundHandler<Object> {

    private final String firstMessage;

    private int counter;

    public TimeClientHandler(){
        firstMessage = "QUERY TIME ORDER" + System.getProperty("line.separator");
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        String body  = (String) msg;
        System.out.println("Now is : "+body +"; the counter is " + ++counter);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<100;i++){
            ByteBuf message = Unpooled.copiedBuffer(firstMessage,CharsetUtil.UTF_8);
            ctx.writeAndFlush(message);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
