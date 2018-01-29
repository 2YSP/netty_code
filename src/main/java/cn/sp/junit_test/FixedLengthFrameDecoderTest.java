package cn.sp.junit_test;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: 2YSP
 * @Description: 使用EmbeddedChannel进行单元测试
 * @Date: Created in 2018/1/29
 */
public class FixedLengthFrameDecoderTest {

    /**
     * 测试入站消息
     */
    @Test
    public void testFrameDecoded() {
        ByteBuf buffer = Unpooled.buffer();
        //存储9个字节
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }

        ByteBuf input = buffer.duplicate();
        //以3字节的帧长度被测试
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        // write bytes
        assert channel.writeInbound(input.retain());

        assert channel.finish();

        //read messages
        ByteBuf read = channel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals(buffer.readSlice(3), read);
        read.release();

        assertNull(channel.readInbound());
        buffer.release();

    }
}
