package cn.sp.heartbeat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * Created by 2YSP on 2019/5/23.
 */
@ChannelHandler.Sharable
public class HeartBeatRespHandler extends SimpleChannelInboundHandler<String> {

    private static final String resp = "I have received successfully$_";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.equals("start notify with server")){
            ctx.writeAndFlush(Unpooled.copiedBuffer("ok$_".getBytes()));
        }else {
            //返回心跳应答信息
            System.out.println(new Date().toString()+" Receive client heart beat message: ---->"+ msg);
            ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
        }
    }

}
