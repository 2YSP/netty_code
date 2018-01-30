package cn.sp.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

import java.util.List;

/**
 * @Author: 2YSP
 * @Description: 编解码器演示
 * @Date: Created in 2018/1/30
 */
public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame, WebSocketConvertHandler.MyWebSocketFrame> {

    /**
     * 编码
     *
     * @param channelHandlerContext
     * @param msg
     * @param list
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyWebSocketFrame msg, List<Object> list) throws Exception {
        ByteBuf payload = msg.getData().duplicate();
        switch (msg.getType()) {
            case BINARY:
                list.add(new BinaryWebSocketFrame(payload));
                break;
            case PING:
                list.add(new PingWebSocketFrame(payload));
                break;
            case PONG:
                list.add(new PongWebSocketFrame(payload));
                break;
            case TEXT:
                list.add(new TextWebSocketFrame(payload));
                break;
            case CLOSE:
                list.add(new CloseWebSocketFrame(true, 0, payload));
                break;
            case CONTINUATION:
                list.add(new ContinuationWebSocketFrame(payload));
                break;
            default:
                throw new IllegalStateException("Unsupported webSocket msg:" + msg);
        }

    }

    /**
     * 解码
     *
     * @param channelHandlerContext
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.content().duplicate().retain();
        if (msg instanceof BinaryWebSocketFrame) {

            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.BINARY, payload));
        } else if (msg instanceof CloseWebSocketFrame) {

            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CLOSE, payload));
        } else if (msg instanceof PingWebSocketFrame) {

            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PING, payload));
        } else if (msg instanceof PongWebSocketFrame) {

            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.PONG, payload));
        } else if (msg instanceof TextWebSocketFrame) {

            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.TEXT, payload));
        } else if (msg instanceof ContinuationWebSocketFrame) {

            out.add(new MyWebSocketFrame(MyWebSocketFrame.FrameType.CONTINUATION, payload));
        }else {
            throw new IllegalStateException("Unsupported webSocket msg:" + msg);
        }
    }

    public static final class MyWebSocketFrame {
        public enum FrameType {
            BINARY,
            CLOSE,
            PING,
            PONG,
            TEXT,
            CONTINUATION
        }

        private final FrameType type;
        private final ByteBuf data;

        public MyWebSocketFrame(FrameType type, ByteBuf data) {
            this.type = type;
            this.data = data;
        }

        public FrameType getType() {
            return type;
        }

        public ByteBuf getData() {
            return data;
        }
    }
}
