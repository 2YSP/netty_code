package cn.sp.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;


/**
 * Created by 2YSP on 2019/4/30.
 */
// 标识一个ChannelHandler可以被多个Channel安全的共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: "+in.toString(CharsetUtil.UTF_8));
        ChannelFuture future = ctx.write(in);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("===========Server write success");
                }else {
                    System.out.println("Write Error");
                    channelFuture.cause().printStackTrace();
                }
            }
        });

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
