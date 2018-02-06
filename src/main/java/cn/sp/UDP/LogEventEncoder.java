package cn.sp.UDP;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Author: 2YSP
 * @Description:
 * @Date: Created in 2018/2/6
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent>{

    private final InetSocketAddress remoteAddress;

    public LogEventEncoder(InetSocketAddress remoteAddress){
        this.remoteAddress = remoteAddress;
    }


    @Override
    protected void encode(ChannelHandlerContext context, LogEvent logEvent, List<Object> list) throws Exception {
        byte[] file = logEvent.getLogfile().getBytes(CharsetUtil.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = context.alloc().buffer(file.length+msg.length+1);
        buf.writeBytes(file);
        buf.writeByte(LogEvent.SEPARATOR);
        buf.writeBytes(msg);
        list.add(new DatagramPacket(buf.array(),buf.array().length,remoteAddress));
    }
}
