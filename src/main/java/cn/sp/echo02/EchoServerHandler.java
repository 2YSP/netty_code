package cn.sp.echo02;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;


/**
 * Created by 2YSP on 2019/4/30.
 */
// 标识一个ChannelHandler可以被多个Channel安全的共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("This is " + ++counter +" times receive client :[" +body+"]");
        body += "$_";
        ctx.writeAndFlush(Unpooled.copiedBuffer(body.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将消息冲刷到远程节点，并且关闭channel
       ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("============发生了异常："+cause.getMessage());
        cause.printStackTrace();
        // 关闭该channel
        ctx.close();
    }
}
