package cn.sp.junit_test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: 2YSP
 * @Description:
 * @Date: Created in 2018/1/29
 */
public class AbsIntegerEncoderTest {

    /**
     * 测试出站消息
     */
    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());

        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());

        //read bytes
        for (int i = 1; i < 10; i++) {
//            assertEquals(i, (int) channel.readOutbound());
        }

        assertNull(channel.readOutbound());
    }
}
