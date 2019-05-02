package cn.sp.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * @Author: 2YSP
 * @Description:
 * @Date: Created in 2018/1/19
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String body = byteBuf.toString(CharsetUtil.UTF_8);
        body = body.substring(0,body.length() - System.getProperty("line.separator").length());
        System.out.println("The time server receive order: "+body +"; the counter is " + ++counter);
        String currentTime = "query  time order".equalsIgnoreCase(body) ? new Date().toString():"BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ctx.write(Unpooled.copiedBuffer(currentTime.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 会关闭客户端
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        // 不会
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
    }
}
