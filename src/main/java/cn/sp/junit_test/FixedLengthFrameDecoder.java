package cn.sp.junit_test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author: 2YSP
 * @Description:
 * @Date: Created in 2018/1/29
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength){
        if (frameLength <= 0){
            throw new IllegalArgumentException("frameLength must be a positive integer:"+frameLength);
        }
        this.frameLength = frameLength;
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        while (in.readableBytes() >= frameLength){
            //从ByteBuf中读取一个新帧
            ByteBuf buf = in.readBytes(frameLength);
            list.add(buf);
        }
    }
}
