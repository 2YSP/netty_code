package cn.sp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @Author: 2YSP
 * @Description:
 * @Date: Created in 2018/1/26
 */
public class ByteBufTest {

    public static void main(String[] args) {
        test03();
    }

    /**
     * 对ByteBuf进行切片
     */
    public static void test01(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
        //新的分片
        ByteBuf sliced = buf.slice(0, 15);
        System.out.println(sliced.toString(utf8));
        buf.setByte(0,(byte)'J');
        //将会成功，因为数据是共享的，对其中一个所做的更改对另外一个也是可见的
        System.out.println(buf.getByte(0) == sliced.getByte(0));
    }

    /**
     * 复制一个ByteBuf
     */
    public static void test02(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);

        ByteBuf copy = buf.copy(0, 15);
        System.out.println(copy.toString(utf8));
        buf.setByte(0,(byte)'J');
        //输出true，因为数据不是共享的
        System.out.println(copy.getByte(0) != buf.getByte(0));
    }

    /**
     * get()和set()方法的使用
     */
    public static void test03(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
        //打印首字母'N'
        System.out.println((char)buf.getByte(0));
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.setByte(0, (byte) 'B');
        System.out.println((char)buf.getByte(0));
        // true,因为这些操作不会修改相应的索引
        System.out.println(readerIndex == buf.readerIndex());
        System.out.println(writerIndex == buf.writerIndex());
    }
}
