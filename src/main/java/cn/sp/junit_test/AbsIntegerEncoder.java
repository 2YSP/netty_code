package cn.sp.junit_test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @Author: 2YSP
 * @Description:
 * @Date: Created in 2018/1/29
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        while (in.readableBytes() >= 4){
            //取绝对值
            int value = Math.abs(in.readInt());
            list.add(value);
        }
    }
}
